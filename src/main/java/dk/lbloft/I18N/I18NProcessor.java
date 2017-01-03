package dk.lbloft.I18N;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;


@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"dk.lbloft.I18N.Message"})
public class I18NProcessor extends AbstractProcessor {
	private static final Logger logger = Logger.getLogger(I18NProcessor.class.getName());
	private FileObject file;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		try {
			file = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "", "translation.properties");
			processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Writing file: " + file.toUri());
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Unable to createResource: " + e.getMessage());
		}
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Process");
		try(Writer out = file.openWriter()) {
			for (TypeElement annotation : annotations) {
				for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
					for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
						if(annotationMirror.getAnnotationType().asElement().toString().equals(annotation.toString())) {
							Properties prop = new Properties();
							for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
								prop.put(entry.getKey().getSimpleName().toString(), entry.getValue().getValue().toString());
							}
							String key = String.format("%s.%s", element.getEnclosingElement().getSimpleName(), prop.getProperty("key", element.getSimpleName().toString()));
							String value = prop.getProperty("value", "N/A");
							out.write(key + "=" + value + "\n");
						}
					}
				}
			}
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Unable to write to file: " + e.getMessage());
		}
		return false;
	}
}