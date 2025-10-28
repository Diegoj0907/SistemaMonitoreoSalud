package Modelo;

import java.util.*;

public class AnalizadorTexto {

    private static final Set<String> palabrasPositivas = new HashSet<>(Arrays.asList(
        "bien","feliz","contento","alegre","tranquilo","gracias","genial","bueno","emocionado"
    ));
    private static final Set<String> palabrasNegativas = new HashSet<>(Arrays.asList(
        "triste","mal","deprimido","ansioso","angustiado","enojado","odio","suicid","suicidio","nunca","no puedo"
    ));
    private static final Set<String> palabrasAlarma = new HashSet<>(Arrays.asList(
        "suicidar","matarme","lastimarme","no quiero vivir","acabará conmigo"
    ));

    public AnalizadorTexto() {}

    public ResultadoAnalisis analizar(String texto) {
        if (texto == null) texto = "";
        String lower = texto.toLowerCase();
        double score = 0.0;
        int pos = 0, neg = 0, alarm = 0;

        String[] tokens = lower.split("\\W+");
        for (String t : tokens) {
            if (t.isEmpty()) continue;
            if (palabrasPositivas.contains(t)) { pos++; score += 1.0; }
            if (palabrasNegativas.contains(t)) { neg++; score -= 1.0; }
            if (palabrasAlarma.contains(t)) { alarm++; score -= 3.0; }
        }

        String emocion = "neutral";
        if (alarm > 0) emocion = "alarma";
        else if (score > 0.5) emocion = "positivo";
        else if (score < -0.5) emocion = "negativo";

        double puntaje = Math.max(-1.0, Math.min(1.0, score / Math.max(1, tokens.length)));

        return new ResultadoAnalisis(emocion, puntaje, pos, neg, alarm);
    }

    public static class ResultadoAnalisis {
        public final String emocion;
        public final double puntaje;
        public final int conteoPos;
        public final int conteoNeg;
        public final int conteoAlarma;
        public ResultadoAnalisis(String emocion, double puntaje, int cp, int cn, int ca) {
            this.emocion = emocion; this.puntaje = puntaje; this.conteoPos = cp; this.conteoNeg = cn; this.conteoAlarma = ca;
        }
    }
}
