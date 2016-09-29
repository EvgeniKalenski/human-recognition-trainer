package step1

class DataCollector {

    public static final String IMAGE_NET_DOWNLOAD_PATH = 'http://image-net.org/api/text/imagenet.synset.geturls?wnid='
    public static final String WOMEN_ID = 'n10787470'


    public static void main(String[] args) {
        new File('women').mkdir()
        FileDownloader.downloadFile("$IMAGE_NET_DOWNLOAD_PATH$WOMEN_ID", 'women/women_list')
        new File('women/data2').mkdir()
        def fileName = 1
        new File('women/women_list').eachLine { line ->
            try {
                FileDownloader.downloadFile(line, "women/data2/${fileName}.jpg")
                fileName++
            } catch (Exception e) {
                println "Can not download image from url: $line"
            }
        }
    }

}
