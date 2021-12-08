import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) {
        try
        {
            Document document = Jsoup.connect("https://lenta.ru/") //нужный сайт
                    .userAgent("Chrome/81.0.4044.138") //с какого устройства
                    .referrer("http://www.google.com") //с какого сайта переходим
                    .get(); //получаем страничку
            Elements elements = document.select("img"); //ищем теги img
            elements.forEach(element -> { //пробегаемся по каждому тегу
                try{
                    String url = element.attr("src"); // получаем url картинки из аттрибута src
                    String[] strings = url.split("/"); //сплитим ссылку по косой черте
                    String output = ".\\target\\out\\"
                            + strings[strings.length - 1]; //составляем путь файла
                    if (url.matches("^(https?:\\/\\/)?([\\w-]{1,32}\\.[\\w-]{1,32})[^\\s@]*.jpg$")){ //проверяем формат ссылки + наличие расширения img
                        URLConnection connection = new URL(url).openConnection();  //открываем соединение
                        InputStream stream = connection.getInputStream(); //открывем stream
                        Files.createDirectories(Path.of(output)); //создаем директорию для копирования
                        Files.copy(stream, new File(output).toPath(), StandardCopyOption.REPLACE_EXISTING); //копиреум наши файлы
                    }
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }
            });

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
