import java.awt.image.RenderedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class App {
    public static void main(String[] args) throws Exception {
        

        // fazer conexao HTTP e buscar os top 250 filmes
        String url = "https://raw.githubusercontent.com/lukadev08/lukadev08.github.io/main/apidata/imdbtop250moviesdata.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
     
        
        // pegar só os dados interessantes(titulo, poster, cassificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        
        //exibir e manipular os dados
        for (Map<String,String> filme : listaDeFilmes) {
            
            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
           
            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = titulo + ".png";
            
            var geradora = new GeradoraDeFigurinhas();
            geradora.cria(inputStream, nomeArquivo);
                
            System.out.println(titulo);
            System.out.println();
        
        
           }

    }
}
