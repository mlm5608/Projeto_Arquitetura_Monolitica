/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Aluno
 */
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final Path ARQUIVO = Paths.get("historico", "historico.csv");
    private static final String[] OPERACOES = {
        "Somar", "Subtrair", "Multiplicar", "Dividir", "Potenciação", "Radiciação"
    };

    private static JFrame janela;
    private static JComboBox<String> cbOperacao;
    private static JTextField tfA, tfB;
    private static JLabel lbA, lbB, lbResultado;
    private static JCheckBox ckResto;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::criarTela);
    }

    // ---------- Interface ----------
    private static void criarTela() {
        janela = new JFrame("Calculadora Java");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        cbOperacao = new JComboBox<>(OPERACOES);
        tfA = new JTextField();
        tfB = new JTextField();
        lbA = new JLabel("Primeiro número:");
        lbB = new JLabel("Segundo número:");
        ckResto = new JCheckBox("Divisão com resto (só inteiros)");
        ckResto.setEnabled(false);

        form.add(new JLabel("Operação:"));
        form.add(cbOperacao);
        form.add(lbA);
        form.add(tfA);
        form.add(lbB);
        form.add(tfB);
        form.add(new JLabel(""));
        form.add(ckResto);

        cbOperacao.addActionListener(e -> atualizarRotulos());

        JButton btCalcular = new JButton("Calcular");
        JButton btHistorico = new JButton("Ver histórico");
        JButton btSair = new JButton("Sair");
        btCalcular.addActionListener(e -> calcular());
        btHistorico.addActionListener(e -> exibirHistorico());
        btSair.addActionListener(e -> System.exit(0));

        JPanel botoes = new JPanel(new FlowLayout());
        botoes.add(btCalcular);
        botoes.add(btHistorico);
        botoes.add(btSair);

        lbResultado = new JLabel("Resultado: ", SwingConstants.CENTER);
        lbResultado.setFont(lbResultado.getFont().deriveFont(Font.BOLD, 15f));
        lbResultado.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        janela.add(form, BorderLayout.NORTH);
        janela.add(botoes, BorderLayout.CENTER);
        janela.add(lbResultado, BorderLayout.SOUTH);
        janela.setSize(430, 310);
        janela.setLocationRelativeTo(null);
        janela.getRootPane().setDefaultButton(btCalcular);
        janela.setVisible(true);
    }

    private static void atualizarRotulos() {
        String op = (String) cbOperacao.getSelectedItem();
        ckResto.setEnabled("Dividir".equals(op));
        if (!ckResto.isEnabled()) ckResto.setSelected(false);
        switch (op) {
            case "Dividir":     lbA.setText("Dividendo:"); lbB.setText("Divisor:"); break;
            case "Potenciação": lbA.setText("Base:");      lbB.setText("Expoente:"); break;
            case "Radiciação":  lbA.setText("Radicando:"); lbB.setText("Índice:"); break;
            default:            lbA.setText("Primeiro número:"); lbB.setText("Segundo número:");
        }
    }

    // ---------- Cálculo ----------
    private static void calcular() {
        double a, b;
        try {
            a = Double.parseDouble(tfA.getText().trim().replace(',', '.'));
            b = Double.parseDouble(tfB.getText().trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            erro("Entrada inválida: digite apenas números.");
            return;
        }

        String op = (String) cbOperacao.getSelectedItem();
        String nomeOp, resultado;

        switch (op) {
            case "Somar":
                nomeOp = "SOMA"; resultado = fmt(a + b); break;
            case "Subtrair":
                nomeOp = "SUBTRACAO"; resultado = fmt(a - b); break;
            case "Multiplicar":
                nomeOp = "MULTIPLICACAO"; resultado = fmt(a * b); break;
            case "Dividir":
                boolean comResto = ckResto.isSelected();
                nomeOp = comResto ? "DIVISAO_COM_RESTO" : "DIVISAO_SEM_RESTO";
                if (b == 0) { erro("Divisão por zero não é permitida."); return; }
                if (comResto) {
                    if (a != Math.rint(a) || b != Math.rint(b)) {
                        erro("Divisão com resto exige números inteiros."); return;
                    }
                    long q = (long) a / (long) b;
                    long r = (long) a % (long) b;
                    resultado = "quociente=" + q + ";resto=" + r;
                } else {
                    resultado = fmt(a / b);
                }
                break;
            case "Potenciação":
                nomeOp = "POTENCIACAO";
                if (a == 0 && b < 0) { erro("0 elevado a expoente negativo é indeterminado."); return; }
                resultado = fmt(Math.pow(a, b)); break;
            default: // Radiciação
                nomeOp = "RADICIACAO";
                if (b == 0) { erro("Índice da raiz igual a zero é indefinido."); return; }
                boolean indiceInteiro = b == Math.rint(b);
                boolean indicePar = indiceInteiro && b % 2 == 0;
                if (a < 0 && !(indiceInteiro && !indicePar)) {
                    erro("Radicando negativo com índice par (ou fracionário) não tem resultado real.");
                    return;
                }
                double r = a < 0 ? -Math.pow(-a, 1.0 / b) : Math.pow(a, 1.0 / b);
                resultado = fmt(r);
        }

        lbResultado.setText("Resultado: " + resultado);
        registrarHistorico(nomeOp, fmt(a), fmt(b), resultado);
    }

    private static String fmt(double d) {
        if (d == Math.rint(d) && Math.abs(d) < 1e15) return String.valueOf((long) d);
        return String.valueOf(d);
    }

    private static void erro(String msg) {
        lbResultado.setText("Resultado: —");
        JOptionPane.showMessageDialog(janela, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    // ---------- Histórico ----------
    private static void registrarHistorico(String op, String a, String b, String res) {
        try {
            Files.createDirectories(ARQUIVO.getParent());
            if (!Files.exists(ARQUIVO)) {
                Files.write(ARQUIVO, "data_hora,operacao,operando1,operando2,resultado\n"
                        .getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            }
            String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String linha = String.join(",", data, op, a, b, res) + "\n";
            Files.write(ARQUIVO, linha.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(janela, "Não foi possível gravar o histórico.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void exibirHistorico() {
        try {
            if (!Files.exists(ARQUIVO) || Files.size(ARQUIVO) == 0) {
                JOptionPane.showMessageDialog(janela, "Ainda não há histórico registrado.");
                return;
            }
            String conteudo = new String(Files.readAllBytes(ARQUIVO), StandardCharsets.UTF_8);
            JTextArea area = new JTextArea(conteudo, 15, 45);
            area.setEditable(false);
            JOptionPane.showMessageDialog(janela, new JScrollPane(area),
                    "Histórico", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException ex) {
            erro("Erro ao ler o histórico.");
        }
    }
}