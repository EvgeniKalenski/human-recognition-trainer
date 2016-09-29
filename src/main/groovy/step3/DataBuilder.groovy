package step3

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Rect
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.objdetect.CascadeClassifier

import java.nio.file.Paths

class DataBuilder {

//create info.lst file

    public static final String FRONTAL_FACE_RECOGNITION = "lbpcascade_frontalface.xml";

    static {
        Runtime.getRuntime().loadLibrary0(GroovyClassLoader.class, Core.NATIVE_LIBRARY_NAME)
    }


    public static void main(String[] args) {

        File trainingFacesDir = new File("trainingFaces");
        File infoList = new File("info.lst");
        CascadeClassifier faceDetection = new CascadeClassifier(Paths.get("./src/main/resources/$FRONTAL_FACE_RECOGNITION").toFile().getCanonicalPath());
        if (faceDetection.empty()) {
            System.err.println("Empty or corrupted file");
        } else {
            trainingFacesDir.eachFile {

                Mat image = Imgcodecs.imread(it.getCanonicalPath());
                MatOfRect faceDetections = new MatOfRect();
                faceDetection.detectMultiScale(image, faceDetections);
                int facesCount = faceDetections.toArray().length;
                if (facesCount == 1) { //todo how deal with multi-faces?
                    for (Rect rect : faceDetections.toArray()) {
                        infoList.append("${it.path} $facesCount ${rect.x} ${rect.y} ${rect.x + rect.width} ${rect.y + rect.height}\n")
                    }
                }
            }
        }
    }


}
