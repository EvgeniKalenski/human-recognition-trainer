import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class Clearing {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        File womenData = new File("women/data");
        new File("trainingFaces").mkdir();
        File ugly = new File("ugly");
        outer:
        for (File file : womenData.listFiles()) {
            for (File uglyFile : ugly.listFiles()) {
                Mat uglyImg = imread(uglyFile.getPath());
                Mat questionImg = imread(file.getPath());
                if (questionImg.empty()) {
                    System.out.println("Empty image: " + file.getPath());
                    file.delete();
                    continue outer;
                }
                Mat dst = new Mat(questionImg.size(), questionImg.type());
                if (uglyImg.size().equals(questionImg.size())) {
                    Core.bitwise_xor(uglyImg, questionImg, dst);
                    if (!dst.empty()) {
                        file.delete();
                        System.out.println("Broken image: " + file.getPath());
                        continue outer;
                    }
                }
            }
            file.renameTo(new File("trainingFaces/1-" + file.getName()));
        }
    }

}
