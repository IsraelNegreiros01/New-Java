package _2_File_e_Buffered._1_PlaylistMúsicas.Entities;

public class Musica {

    private String titulo;
    private String artista;
    private int anoLancamento;
    private Genero genero;

    public Musica(String titulo, String artista,
                  int anoLancamento, Genero genero) {

        this.titulo = titulo;
        this.artista = artista;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public Genero getGenero() {
        return genero;
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo
                + " | Artista: " + artista
                + " | Ano: " + anoLancamento
                + " | Genero: " + genero;
    }
}