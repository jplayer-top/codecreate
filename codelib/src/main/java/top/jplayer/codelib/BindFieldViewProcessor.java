package top.jplayer.codelib;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by Obl on 2019/7/13.
 * top.jplayer.codelib
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@AutoService(Processor.class)
public class BindFieldViewProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        // 添加了关注的注解
        types.add(BindFieldView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindFieldView.class);
        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            String fullClassName = classElement.getQualifiedName().toString();
            System.out.println(fullClassName);
            BindFieldView bindAnnotation = variableElement.getAnnotation(BindFieldView.class);
            int id = bindAnnotation.id();

            ClassName activity = ClassName.get("android.app", "Activity");

            TypeSpec.Builder mainActivityBuilder = TypeSpec.classBuilder("MainActivity")
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(activity);

            ClassName override = ClassName.get("java.lang", "Override");

            ClassName bundle = ClassName.get("android.os", "Bundle");

            ClassName nullable = ClassName.get("android.support.annotation", "Nullable");

            ParameterSpec savedInstanceState = ParameterSpec.builder(bundle, "savedInstanceState")
                    .addAnnotation(nullable)
                    .build();

            MethodSpec onCreate = MethodSpec.methodBuilder("onCreate")
                    .addAnnotation(override)
                    .addModifiers(Modifier.PROTECTED)
                    .addParameter(savedInstanceState)
                    .addStatement("super.onCreate(savedInstanceState)")
                    .addStatement("setContentView(R.layout.activity_main)")
                    .build();

            TypeSpec mainActivity = mainActivityBuilder.addMethod(onCreate)
                    .build();

            JavaFile javaFile = JavaFile.builder("com.test", mainActivity).build();
            File file = new File("codelib/src/main/java");
            try {
                javaFile.writeTo(System.out);
                javaFile.writeTo(file);
                System.out.println(file.getAbsolutePath());
                System.out.println(file.exists());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }
        return true;

    }
}