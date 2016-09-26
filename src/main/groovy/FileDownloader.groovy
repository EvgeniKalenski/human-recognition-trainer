class FileDownloader {

    static void downloadFile(String url, String filePath) {
        def fileOutputStream = new FileOutputStream(filePath)
        def out = new BufferedOutputStream(fileOutputStream)
        out << new URL(url).openStream()
        out.close()
    }

}
