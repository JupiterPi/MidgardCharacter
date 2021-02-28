package jupiterpapi.midgardcharacter.frontend.sitesviewer;

import jupiterpi.tools.files.Path;
import jupiterpi.tools.files.TextFile;
import jupiterpi.tools.util.AppendingList;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Loader {
    private Path subdir(String name) {
        return Path.getRunningDirectory().subdir("sv").subdir(name);
    }

    /* pages */

    public String loadPage(String pageName) {
        String frameTop = loadPageContent("frameTop.html");
        String frameBottom = loadPageContent("frameBottom.html");
        String body = loadPageContent(pageName + ".html");
        return frameTop + "\n" + body + "\n" + frameBottom;
    }

    private String loadPageContent(String fileName) {
        return readFile(subdir("pages").file(fileName));
    }

    /* res */

    public String getTextResource(String fileName) {
        return readFile(subdir("text").file(fileName));
    }

    public ResponseEntity<byte[]> getPictureResource(String fileName) {
        return getPictureResource(subdir("pic").file(fileName));
    }

    private ResponseEntity<byte[]> getPictureResource(Path file) {
        try {
            HttpHeaders headers = new HttpHeaders();
            InputStream in = new FileInputStream(file.file());
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            byte[] byteArray = buffer.toByteArray();

            return new ResponseEntity<>(byteArray, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* util */

    private String readFile(Path file) {
        return new TextFile(file).getFileForOutput();
    }
}