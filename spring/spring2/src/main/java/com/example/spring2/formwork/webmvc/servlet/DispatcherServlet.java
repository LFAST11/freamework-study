package com.example.spring2.formwork.webmvc.servlet;

import com.example.spring2.formwork.annotation.DiyController;
import com.example.spring2.formwork.annotation.DiyRequestMapper;
import com.example.spring2.formwork.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * lingfan
 * 2019-08-26 13:49
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {

    private final String location = "contextConfigLocation";

    private ApplicationContext applicationContext;

    private List<HandleMapper> handleMappers = new ArrayList();

    private Map<HandleMapper,HandleAdapter> handleAdapterMap = new HashMap<HandleMapper, HandleAdapter>();

    private List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        applicationContext = new ApplicationContext(config.getInitParameter(location));

        initStrategies(applicationContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            doDispatcher(req,resp);

        }catch (Exception e){
            resp.getWriter().write("500 Exception,Details:\r\n" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "").replaceAll(",\\s", "\r\n"));
            e.printStackTrace();
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HandleMapper handle = getHandle(req);

        if(handle == null){
            processDispatcherResult(req,resp,new ModelAndView("404"));
            return ;
        }

        //准备参数
        HandleAdapter ha = getHandleAdapter(handle);

        ModelAndView modelAndView = ha.handle(req, resp, handle);


        processDispatcherResult(req,resp,modelAndView);

    }

    private HandleAdapter getHandleAdapter(HandleMapper handle) {
        if(handleAdapterMap == null) return null;
        HandleAdapter handleAdapter = handleAdapterMap.get(handle);
        if(handleAdapter.supports(handle)){
            return handleAdapter;
        }

        return null;
    }

    public void processDispatcherResult(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) throws Exception {
        if(modelAndView == null )  return ;

        for (ViewResolver viewResolver : this.viewResolvers) {
            View view = viewResolver.resolveViewName(modelAndView.getViewName(), null);
            view.render(modelAndView.getModel(),request,response);
            return ;
        }
    }

    private HandleMapper getHandle(HttpServletRequest req) {
        if(this.handleMappers.isEmpty()) return null;

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replace("/+","/");
        for (HandleMapper handleMapper : this.handleMappers) {
            Pattern pattern = handleMapper.getPattern();
            Matcher matcher = pattern.matcher(url);
            if(matcher.matches()){
                return handleMapper;
            }
        }
        return null;
    }

    private void initStrategies(ApplicationContext context) {

        //初始化9大组件
        initMultipartResolver(context);
        initLocaleResolver(context);
        initThemeResolver(context);
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        initViewResolvers(context);
        initFlashMapManager(context);
    }

    private void initHandlerAdapters(ApplicationContext context) {

        //一个handleMapper对应一个handleAdapter
        for (HandleMapper handleMapper : this.handleMappers) {
            handleAdapterMap.put(handleMapper,new HandleAdapter());
        }
    }

    private void initHandlerMappings(ApplicationContext context) {
        String[] beanDefinitionNames = context.getBeanDefinitionName();

        for (String beanName : beanDefinitionNames) {

            try {
                Object controller = context.getBean(beanName);

                Class<?> aClass = controller.getClass();
                if(!aClass.isAnnotationPresent(DiyController.class)) continue;



                String baseUrl = null;
                if(aClass.isAnnotationPresent(DiyRequestMapper.class)){
                    DiyRequestMapper annotation = aClass.getAnnotation(DiyRequestMapper.class);
                    baseUrl = annotation.value();
                }

                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    if(method.isAnnotationPresent(DiyRequestMapper.class)){
                        DiyRequestMapper methodMapper = method.getAnnotation(DiyRequestMapper.class);
                        String detailUrl = methodMapper.value();
                        String regex = (baseUrl+"/"+detailUrl).replaceAll("/+","/");
                        Pattern pattern = Pattern.compile(regex);
                        handleMappers.add(new HandleMapper(controller,method,pattern));
                        log.debug("mapper "+regex+" method "+method);
                    }
                }



            } catch (Exception e) {
                log.error("initHandlerAdapters error",e);
            }
        }
    }

    private void initViewResolvers(ApplicationContext context) {
        String templatePath = context.getconfig().getProperty("templatePath");
        String templateFilePath = this.getClass().getClassLoader().getResource(templatePath).getFile();

        File templateFile = new File(templateFilePath);

        for(String name :templateFile.list()){
            this.viewResolvers.add(new ViewResolver(templatePath));
        }

    }

    private void initLocaleResolver(ApplicationContext context) {

    }

    private void initFlashMapManager(ApplicationContext context) {

    }



    private void initRequestToViewNameTranslator(ApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {

    }



    private void initThemeResolver(ApplicationContext context) {
    }



    private void initMultipartResolver(ApplicationContext context) {

    }

}
