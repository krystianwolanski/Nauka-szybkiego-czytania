package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.IOException;



public class Controller {

    @FXML
    TextField jaka_litera, od_slow, do_slow, od_liter, do_liter, ile_slow_tf;

    @FXML
    Text jaka_litera_warning, ilosc_liter_w_slowie_warning, ile_slow_warning, ile_razy_wybrana_warning;

    static Character litera;
    static int od_ilu_liter_slowo,do_ilu_liter_slowo,od_ilu_tych_samych_liter,do_ilu_tych_samych_liter, ile_slow_tf_s;
    String default_style;


    void nasluchuj_zmiane(TextField textField){
        textField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (textField.getText().length() >1) {

                        textField.setText(textField.getText().substring(0, 1));

                    }
                    litera = textField.getText().charAt(0);
                    if(litera>=97 && litera<=122){
                        litera=(char)((int)litera-32);
                        textField.setText(litera.toString());

                    }
                }
            }});
    }

    @FXML
    void initialize() throws IOException {
        nasluchuj_zmiane(jaka_litera);

        jaka_litera_warning.setVisible(false);
        ilosc_liter_w_slowie_warning.setVisible(false);
        ile_slow_warning.setVisible(false);
        ile_razy_wybrana_warning.setVisible(false);
        default_style = jaka_litera.getStyle();

    }

    void nieprawidlowa_wartosc(Text text){
        text.setText("Nieprawidłowy znak");
        text.setVisible(true);
        text.setStyle("-fx-text-box-border: #ff1010 ; -fx-focus-color: red;");
        throw new ExceptionInInitializerError();
    }
    @FXML
    void zatwierdz_on_action() throws IOException {

        try{
            ile_slow_tf_s=Integer.parseInt(ile_slow_tf.getText());
        }
        catch(Exception e){
            nieprawidlowa_wartosc(ile_slow_warning);
        }

        try{
            litera = jaka_litera.getText().charAt(0);
        }
        catch (Exception e){
            nieprawidlowa_wartosc(jaka_litera_warning);
        }
        jaka_litera_warning.setVisible(false);

        if(litera>=97 && litera<=122){
            litera=(char)((int)litera-32);
            jaka_litera.setText(litera.toString());
        }


        if(litera<65 || litera >90 ){
            nieprawidlowa_wartosc(jaka_litera_warning);
        }
        else{
            jaka_litera_warning.setVisible(false);
            jaka_litera.setStyle(default_style);
        }



        try {
            od_ilu_liter_slowo=Integer.parseInt(od_slow.getText());
            do_ilu_liter_slowo = Integer.parseInt(do_slow.getText());
        }
        catch (Exception e){

            ilosc_liter_w_slowie_warning.setText("Nieprawidłowa wartość");
            ilosc_liter_w_slowie_warning.setVisible(true);
            throw new ExceptionInInitializerError();

        }

        if(od_ilu_liter_slowo>do_ilu_liter_slowo)
        {
            ilosc_liter_w_slowie_warning.setText("Nieprawidłowa wartość");
            ilosc_liter_w_slowie_warning.setVisible(true);
            throw new ExceptionInInitializerError();
        }
        ilosc_liter_w_slowie_warning.setVisible(false);


        od_ilu_tych_samych_liter = Integer.parseInt(od_liter.getText());
        do_ilu_tych_samych_liter = Integer.parseInt(do_liter.getText());

        if(od_ilu_tych_samych_liter>do_ilu_tych_samych_liter){
            ile_razy_wybrana_warning.setText("Nieprawidłowa wartość");
            ile_razy_wybrana_warning.setVisible(true);
            throw new ExceptionInInitializerError();
        }
        ile_razy_wybrana_warning.setVisible(false);


        Mechanika.max=od_ilu_liter_slowo;
        for(int i=0;i<ile_slow_tf_s;i++)
            Mechanika.zapisz_slowo_do_tablicy();

        Mechanika.create_file();
        Mechanika.zapisz_do_pliku();
    }

}

