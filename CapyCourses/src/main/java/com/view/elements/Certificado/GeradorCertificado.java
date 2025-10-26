package com.view.elements.Certificado;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.view.Modo;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.imageio.ImageIO;

public class GeradorCertificado {
  private static BaseColor BACKGROUND_COLOR;
  private static BaseColor PRIMARY_TEXT;
  private static BaseColor ACCENT_COLOR;
  private static BaseColor SECONDARY_COLOR;
  private static BaseColor GRADIENT_END;
  private static BaseColor WATERMARK_COLOR;

  private static void loadColor() {
    if (Modo.getInstance().getModo()) {
      BACKGROUND_COLOR = new BaseColor(28, 31, 47);
      PRIMARY_TEXT = new BaseColor(255, 255, 255);
      ACCENT_COLOR = new BaseColor(82, 109, 255);
      SECONDARY_COLOR = new BaseColor(137, 160, 255);
      GRADIENT_END = new BaseColor(156, 171, 237);
      WATERMARK_COLOR = new BaseColor(255, 255, 255, 20);
    } else {
      BACKGROUND_COLOR = new BaseColor(255, 255, 255);
      PRIMARY_TEXT = new BaseColor(28, 31, 47);
      ACCENT_COLOR = new BaseColor(51, 204, 255);
      SECONDARY_COLOR = new BaseColor(0, 153, 204);
      GRADIENT_END = new BaseColor(51, 204, 255);
      WATERMARK_COLOR = new BaseColor(0, 0, 0, 20);
    }
  }

  public static void gerarCertificado(
      String nomeAluno, String nomeCurso, int cargaHoraria, String cidade, File diretorio) {
    Document documento = new Document(PageSize.A4.rotate(), 60, 60, 60, 60);
    loadColor();
    try {
      String arquivoPDF = diretorio + "/" + formatarNomeArquivo(nomeCurso + "_" + nomeAluno);
      if (!diretorio.exists()) {
        diretorio.mkdirs();
      }
      PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(arquivoPDF));
      documento.open();

      aplicarFundoCapyCourse(writer, documento.getPageSize());

      Font fonteTitulo = FontFactory.getFont("Helvetica", 34, Font.BOLD, PRIMARY_TEXT);
      Font fonteSubtitulo = FontFactory.getFont("Helvetica-Light", 18, Font.NORMAL, PRIMARY_TEXT);
      Font fonteNome = FontFactory.getFont("Helvetica", 28, Font.BOLD, ACCENT_COLOR);
      Font fonteCurso = FontFactory.getFont("Helvetica", 24, Font.BOLD, SECONDARY_COLOR);
      Font fonteTexto = FontFactory.getFont("Helvetica-Light", 18, Font.NORMAL, PRIMARY_TEXT);
      Font fonteRodape = FontFactory.getFont("Helvetica", 10, Font.NORMAL, GRADIENT_END);

      adicionarMarcaDagua(writer, documento.getPageSize());
      adicionarLogo(documento);
      adicionarConteudoCertificado(
          documento,
          nomeAluno,
          nomeCurso,
          cargaHoraria,
          cidade,
          fonteTitulo,
          fonteSubtitulo,
          fonteNome,
          fonteCurso,
          fonteTexto);

      adicionarAssinaturas(documento, fonteTexto, fonteRodape);

      adicionarElementosSeguranca(documento, writer, fonteRodape);

      documento.close();

    } catch (DocumentException | IOException e) {
      e.printStackTrace();
    }
  }

  private static void aplicarFundoCapyCourse(PdfWriter writer, Rectangle pageSize) {
    PdfContentByte canvas = writer.getDirectContentUnder();

    canvas.setColorFill(BACKGROUND_COLOR);
    canvas.rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight());
    canvas.fill();

    canvas.setColorFill(WATERMARK_COLOR);
    for (float x = 0; x < pageSize.getWidth(); x += 40) {
      for (float y = 0; y < pageSize.getHeight(); y += 40) {
        canvas.circle(x, y, 0.5f);
        canvas.fill();
      }
    }

    PdfContentByte border = writer.getDirectContent();
    float borderWidth = 2f;
    for (float i = 0; i < borderWidth; i += 0.5) {
      border.setColorStroke(
          new BaseColor(
              ACCENT_COLOR.getRed(),
              ACCENT_COLOR.getGreen(),
              ACCENT_COLOR.getBlue(),
              (int) (255 * (1 - i / borderWidth))));
      border.setLineWidth(0.5f);
      adicionarBordaElegante(
          border,
          30 + i,
          30 + i,
          pageSize.getWidth() - 2 * (30 + i),
          pageSize.getHeight() - 2 * (30 + i));
    }
  }

  private static void adicionarMarcaDagua(PdfWriter writer, Rectangle pageSize)
      throws DocumentException {
    PdfContentByte canvas = writer.getDirectContentUnder();

    try {
      Image watermark = Image.getInstance("capycourses/target/classes/capyCourses.png");
      watermark.scalePercent(60);
      watermark.setAbsolutePosition(
          (pageSize.getWidth() - watermark.getScaledWidth()) / 2 + 40,
          (pageSize.getHeight() - watermark.getScaledHeight()) / 2 - 60);
      watermark.setTransparency(new int[] {0x1E, 0x1E});
      canvas.addImage(watermark);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void adicionarBordaElegante(
      PdfContentByte cb, float x, float y, float width, float height) {
    float radius = 20f;
    cb.roundRectangle(x, y, width, height, radius);
    cb.stroke();
  }

  private static void adicionarLogo(Document documento) throws IOException, DocumentException {
    Image logo = Image.getInstance("capycourses/target/classes/capyCourses.png");
    float logoSize = 120;
    logo.scaleToFit(logoSize, logoSize);
    logo.setAbsolutePosition(
        documento.getPageSize().getWidth() - logoSize - 40,
        documento.getPageSize().getHeight() - logoSize - 40);
    documento.add(logo);
  }

  private static void adicionarConteudoCertificado(
      Document documento,
      String nomeAluno,
      String nomeCurso,
      int cargaHoraria,
      String cidade,
      Font fonteTitulo,
      Font fonteSubtitulo,
      Font fonteNome,
      Font fonteCurso,
      Font fonteTexto)
      throws DocumentException {

    adicionarParagrafo(documento, "CERTIFICADO", fonteTitulo, Element.ALIGN_CENTER, 1);
    adicionarParagrafo(documento, "Certificamos que", fonteSubtitulo, Element.ALIGN_CENTER, 1);
    adicionarParagrafo(documento, nomeAluno, fonteNome, Element.ALIGN_CENTER, 1);
    adicionarParagrafo(
        documento, "concluiu com êxito o curso", fonteTexto, Element.ALIGN_CENTER, 1);
    adicionarParagrafo(documento, nomeCurso, fonteCurso, Element.ALIGN_CENTER, 1);
    adicionarParagrafo(
        documento,
        String.format("com carga horária total de %d horas", cargaHoraria),
        fonteTexto,
        Element.ALIGN_CENTER,
        2);
    LocalDate hoje = LocalDate.now();
    DateTimeFormatter formatador =
        DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
    adicionarParagrafo(
        documento,
        String.format("%s, %s", cidade, hoje.format(formatador)),
        fonteTexto,
        Element.ALIGN_CENTER,
        1);
  }

  private static BufferedImage inverterImagem(BufferedImage original) {
    int largura = original.getWidth();
    int altura = original.getHeight();
    BufferedImage invertida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);

    for (int y = 0; y < altura; y++) {
      for (int x = 0; x < largura; x++) {
        int pixel = original.getRGB(x, y);

        int alfa = (pixel >> 24) & 0xff;
        int vermelho = 255 - ((pixel >> 16) & 0xff);
        int verde = 255 - ((pixel >> 8) & 0xff);
        int azul = 255 - (pixel & 0xff);
        int pixelInvertido = (alfa << 24) | (vermelho << 16) | (verde << 8) | azul;
        invertida.setRGB(x, y, pixelInvertido);
      }
    }
    return invertida;
  }

  private static void adicionarAssinaturas(Document documento, Font font, Font fonteRodape)
      throws DocumentException, IOException {
    if ((Modo.getInstance().getModo())) {
      BufferedImage original =
          ImageIO.read(new File("capycourses/src/main/resources/com/img/assinatura.png"));
      BufferedImage processada = inverterImagem(original);

      File tempFile = new File("assinatura_invertida.png");
      ImageIO.write(processada, "png", tempFile);

      Image assinatura = Image.getInstance(tempFile.getAbsolutePath());
      assinatura.scaleToFit(100, 50);
      assinatura.setAlignment(Element.ALIGN_CENTER);
      documento.add(assinatura);
      Paragraph nomeAssinatura = new Paragraph("Diretor CapyCourse", fonteRodape);
      nomeAssinatura.setAlignment(Element.ALIGN_CENTER);
      documento.add(nomeAssinatura);

      tempFile.delete();
    } else {
      Image assinatura = Image.getInstance("capycourses/src/main/resources/com/img/assinatura.png");
      assinatura.scaleToFit(100, 50);
      assinatura.setAlignment(Element.ALIGN_CENTER);
      documento.add(assinatura);
      Paragraph nomeAssinatura = new Paragraph("Diretor CapyCourse", fonteRodape);
      nomeAssinatura.setAlignment(Element.ALIGN_CENTER);
      documento.add(nomeAssinatura);
    }
  }

  private static void adicionarElementosSeguranca(
      Document documento, PdfWriter writer, Font fonteRodape) throws DocumentException {
    String codigoVerificacao = gerarCodigoVerificacao();

    BarcodeQRCode qrCode =
        new BarcodeQRCode(
            "https://capycourse.com/certificados/verificar/" + codigoVerificacao, 90, 90, null);
    Image qrCodeImage = qrCode.getImage();
    qrCodeImage.setAbsolutePosition(50, 50);
    documento.add(qrCodeImage);

    Paragraph verificacao =
        new Paragraph("Verificar autenticidade: " + codigoVerificacao, fonteRodape);
    verificacao.setAlignment(Element.ALIGN_LEFT);
    verificacao.setIndentationLeft(90);
    documento.add(verificacao);
  }

  private static void adicionarParagrafo(
      Document doc, String texto, Font fonte, int alinhamento, int espacos)
      throws DocumentException {
    Paragraph paragrafo = new Paragraph(texto, fonte);
    paragrafo.setAlignment(alinhamento);
    doc.add(paragrafo);
    for (int i = 0; i < espacos; i++) {
      doc.add(new Paragraph("\n"));
    }
  }

  private static String formatarNomeArquivo(String nomeAluno) {
    return nomeAluno.trim().toLowerCase().replace(" ", "_").replaceAll("[^a-z0-9_]", "")
        + "_certificado.pdf";
  }

  private static String gerarCodigoVerificacao() {
    return String.format("%06d", new java.util.Random().nextInt(1000000));
  }

  public static void main(String[] args) {
    File diretorio = new File("C:\\Users\\steph\\Downloads\\TESTE");
    gerarCertificado("João da Silva", "Desenvolvimento Full Stack", 120, "São Paulo", diretorio);
  }
}
