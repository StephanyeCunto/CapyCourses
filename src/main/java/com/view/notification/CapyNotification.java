package com.view.notification;

import java.io.IOException;

public class CapyNotification {
    private static final String APP_NAME = "CapyCourses";
    private static final String ICON_PATH = "C:/Users/steph/Desktop/CapyCourses_LPS/CapyCourses/src/main/resources/capyCoursesNotification.png";

    private static void show(String title, String message, String template) {
        title = title.replace("'", "`'").replace("\"", "`\"");
        message = message.replace("'", "`'").replace("\"", "`\"");
        String iconPath = ICON_PATH.replace("\\", "/");

        String command = String.format(
                "powershell -Command \""
                        + "[Windows.UI.Notifications.ToastNotificationManager, Windows.UI.Notifications, ContentType = WindowsRuntime] | Out-Null; "
                        + "$template = [Windows.UI.Notifications.ToastTemplateType]::%s; "
                        + "$xml = [Windows.UI.Notifications.ToastNotificationManager]::GetTemplateContent($template); "
                        + "$xml.GetElementsByTagName('text')[0].AppendChild($xml.CreateTextNode('%s')); "
                        + "$xml.GetElementsByTagName('text')[1].AppendChild($xml.CreateTextNode('%s')); "
                        + "$image = $xml.GetElementsByTagName('image')[0]; "
                        + "$image.SetAttribute('src', '%s'); "
                        + "$toast = [Windows.UI.Notifications.ToastNotification]::new($xml); "
                        + "$notifier = [Windows.UI.Notifications.ToastNotificationManager]::CreateToastNotifier('%s'); "
                        + "$notifier.Show($toast);\"",
                template, title, message, iconPath, APP_NAME);

        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
            pb.start();
        } catch (IOException e) {
            System.err.println("Erro ao exibir notificação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Notificação simples
            showSimple("Bem-vindo ao CapyCourses! Explore nossos cursos!");
            Thread.sleep(1000);

            // Notificação com imagem (ícone do app)
            showWithTitleAndImage("CapyCourses", "Seu progresso no curso foi atualizado!");
            Thread.sleep(1000);

            // Notificação de sucesso
            showSuccess("Cadastro realizado com sucesso!");
            Thread.sleep(1000);

            // Notificação de aviso (exemplo de expiração de curso)
            showWarning("Seu curso 'Java Básico' está prestes a expirar. Renove agora!");
            Thread.sleep(1000);

            // Notificação de erro (exemplo de falha de conexão)
            showError("Erro ao conectar ao servidor. Tente novamente mais tarde.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void showSimple(String message) {
        show(message, message, "ToastText01");
    }

    public static void showWithTitle(String title) {
        show(title, "", "ToastText02");
    }

    public static void showWithTitleAndImage(String title, String message) {
        show(message, message, "ToastImageAndText02");
    }

    public static void showSuccess(String message) {
        show("✨" + message, "✅ " + message, "ToastImageAndText02");
    }

    public static void showError(String message) {
        show("⚠️ " + message, "❌ " + message, "ToastImageAndText02");
    }

    public static void showWarning(String message) {
        show("📢 " + message, "⚠️ " + message, "ToastImageAndText02");
    }

}