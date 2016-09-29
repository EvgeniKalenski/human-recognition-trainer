package step2

import org.opencv.core.Core
import org.opencv.core.Mat

import java.nio.file.Files

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING
import static org.opencv.imgcodecs.Imgcodecs.imread

public class DataCleaner {

    static {
        Runtime.getRuntime().loadLibrary0(GroovyClassLoader.class, Core.NATIVE_LIBRARY_NAME)
    }

    public static void main(String[] args) throws IOException {

        File womenData = new File("women/data");
        File trainingFaces = new File("trainingFaces");
        trainingFaces.mkdir();
        trainingFaces.eachFile { it ->
            it.delete()
        };
        File ugly = new File("ugly");
        ugly.mkdir();
        outer:
        for (File file : womenData.listFiles()) {
            Mat questionImg = imread(file.getPath());
            if (questionImg.empty()) {
                System.out.println("Empty image: " + file.getPath());
                continue;
            }
            for (File uglyFile : ugly.listFiles()) {
                Mat uglyImg = imread(uglyFile.getPath());
                Mat dst = new Mat(questionImg.size(), questionImg.type());
                if (uglyImg.size().equals(questionImg.size())) {
                    Core.bitwise_xor(uglyImg, questionImg, dst); //
                    if (!dst.empty()) { // todo need to improve condition
                        System.out.println("Broken image: " + file.getPath());
                        continue outer;
                    }
                }
            }
            Files.copy(file.toPath(), new File("trainingFaces/1-" + file.getName()).toPath(), REPLACE_EXISTING);
        }
    }

}
