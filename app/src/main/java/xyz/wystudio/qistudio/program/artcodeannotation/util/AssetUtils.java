package xyz.wystudio.qistudio.program.artcodeannotation.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import xyz.wystudio.qistudio.program.artcodeannotation.App;

public class AssetUtils {

    public static String getFileContent(String filename){
        String result;
        try {
            InputStream inputStream = App.getAssetManager().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            // 如果最后一行添加了换行符，去掉
            if (stringBuilder.charAt(stringBuilder.length() - 1) == '\n') {
                stringBuilder.setLength(stringBuilder.length() - 1);
            }
            reader.close();
            inputStream.close();
            result = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }
}
