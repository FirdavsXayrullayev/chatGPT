package ChatGPT.chat.telebot;

import org.springframework.ai.client.AiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot{

    public TelegramBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }
    @Autowired
    private AiClient aiClient;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            if (update.getMessage().hasText()){
                    System.out.println(update.getMessage().getText());
                    System.out.println(update.getMessage().getFrom().getFirstName());
                    System.out.println(update.getMessage().getChatId());
                    sendMessage(update.getMessage().getChatId(), aiClient.generate(update.getMessage().getText()));
//                    sendPhoto(update.getMessage().getChatId(), "https://images.pexels.com/photos/1590882/pexels-photo-1590882.jpeg");
//                    sendVideo(update.getMessage().getChatId(), "https://media.istockphoto.com/id/1404978793/video/aerial-view-of-green-pasture-against-highest-georgian-mountain-shkhara-near-ushguli-in-georgia.mp4?s=mp4-640x640-is&k=20&c=DHEY7Sr-xYw0qoQ0RxwPICWF-PwgSTgosV0zu-6ULRU=");
//                    sendAudio(update.getMessage().getChatId(), "https://www.chosic.com/download-audio/27950/");
            }
        }
    }

    public void sendMessage(Long chatId, String text){
        var chatIdStr = String.valueOf(chatId);
        var send = new SendMessage(chatIdStr, text);
        try {
            execute(send);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendPhoto(Long chatId, String path){
        var chatIdStr = String.valueOf(chatId);
        var send = new SendPhoto(chatIdStr, new InputFile(path));
        try {
            execute(send);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendVideo(Long chatId, String path){
        var chatIdStr = String.valueOf(chatId);
        var send = new SendVideo(chatIdStr, new InputFile(path));
        try {
            execute(send);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendAudio(Long chatId, String path){
        var chatIdStr = String.valueOf(chatId);
        var send = new SendAudio(chatIdStr, new InputFile(path));
        try {
            execute(send);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "vaIyuta_kursi_bot";
    }
}
