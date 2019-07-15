package top.jplayer.codelib;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor7;
import javax.tools.JavaFileObject;

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
            PackageElement packageOf = processingEnv.getElementUtils().getPackageOf(element);
            System.out.println(packageOf);
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            String fullClassName = classElement.getQualifiedName().toString();
            System.out.println(fullClassName);

            element.accept(new SimpleElementVisitor7<Void, Void>() {
                @Override
                public Void visitType(TypeElement typeElement, Void aVoid) {
                    System.out.println("typeElement");
                    return super.visitType(typeElement, aVoid);
                }

                @Override
                public Void visitExecutable(ExecutableElement executableElement, Void aVoid) {
                    System.out.println("=========================");
                    BindFieldView bindAnnotation = executableElement.getAnnotation(BindFieldView.class);
                    int id = bindAnnotation.id();
                    System.out.println(id);
                    List<? extends VariableElement> parameters = executableElement.getParameters();
                    for (VariableElement parameter : parameters) {
                        System.out.println(parameter.getSimpleName()+"---for");
                        System.out.println(parameter.asType().toString());
                    }
                    System.out.println(executableElement);
                    System.out.println(executableElement.getReturnType());
                    System.out.println(executableElement.getSimpleName());
                    System.out.println(executableElement.getModifiers());
                    System.out.println("=========================");
                    return super.visitExecutable(executableElement, aVoid);
                }

                @Override
                public Void visitPackage(PackageElement packageElement, Void aVoid) {
                    System.out.println("packageElement");
                    return super.visitPackage(packageElement, aVoid);
                }

            }, null);


            ClassName activity = ClassName.get("android.app", "Activity");

            TypeSpec.Builder mainActivityBuilder = TypeSpec.classBuilder("Main111Activity")
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
//                    .addStatement("setContentView(R.layout.activity_main)")
                    .build();
            MethodSpec onCreate1 = MethodSpec.methodBuilder("onPause")
                    .addAnnotation(override)
                    .addModifiers(Modifier.PROTECTED)
                    .build();

            LinkedHashSet<MethodSpec> methodSpecs = new LinkedHashSet<>();
            methodSpecs.add(onCreate);
            methodSpecs.add(onCreate1);
            TypeSpec mainActivity = mainActivityBuilder
                    .addMethods(methodSpecs)
                    .build();

            JavaFile javaFile = JavaFile.builder("com.test", mainActivity).build();
            try {
                Filer filer = processingEnv.getFiler();
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return true;

    }

}
