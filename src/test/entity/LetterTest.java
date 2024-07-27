package test.entity;

import entity.Letter;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LetterTest {
    @Test
    public void testSerialization() {
        Letter letter = new Letter('A', 1);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(letter);
            oos.flush();
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            Letter deserializedLetter = (Letter) ois.readObject();
            ois.close();

            assertEquals(letter, deserializedLetter);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
