package org.max.demo.solid;

/**
 * Датчик температуры - пример с функицоналом sms оповещения
 */
public class TemperatureSensor implements ISensor{

    private int valueActual;

    public TemperatureSensor(int valueMax) {
        this.valueMax = valueMax;
    }

    private int valueMax;

    @Override
    public void getSignal(int signal) {
        this.valueActual = check(signal);
        setAlarm(checkAlert(signal, valueMax), "температура");
    }

    private int check(int signal) {
        return signal * 10;

    }

    @Override
    public void setAlarm(boolean alert, String value) {

    }

    @Override
    public boolean isAlarm() {
        return checkAlert(valueActual, valueMax);
    }
}