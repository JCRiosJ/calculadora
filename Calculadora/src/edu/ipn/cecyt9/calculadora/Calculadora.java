
package edu.ipn.cecyt9.calculadora;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * 
 * Interfaz para nuestra calculadora basica
 * 
 * @author:  emmanuel 
 * @version:  1.0 
 * @date: 06-09-2015 
 */
public class Calculadora extends JFrame {

	/**
	 * generado
	 */
	private static final long serialVersionUID = 1583724102189855698L;

	/** numero tecleado */
	JTextField pantalla;

	/** guarda el resultado de la operacion anterior o el número tecleado */
	double resultado;

        double resultado2;
	/** para guardar la operacion a realizar */
	String operacion;

	/** Los paneles donde colocaremos los botones */
	JPanel panelNumeros, panelOperaciones;

	/** Indica si estamos iniciando o no una operación */
	boolean nuevaOperacion = true;

	/**
	 * Constructor. Crea los botones y componentes de la calculadora
	 */
	public Calculadora() {
		super();
		setSize(300, 300);
		setTitle("Calculadora Simple");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		// Vamos a dibujar sobre el panel
		JPanel panel = (JPanel) this.getContentPane();
		panel.setLayout(new BorderLayout());

		pantalla = new JTextField("0", 20);
		pantalla.setBorder(new EmptyBorder(4, 4, 4, 4));
		pantalla.setFont(new Font("Arial", Font.BOLD, 25));
		pantalla.setHorizontalAlignment(JTextField.RIGHT);
		pantalla.setEditable(false);
		pantalla.setBackground(Color.WHITE);
		panel.add("North", pantalla);

		panelNumeros = new JPanel();
		panelNumeros.setLayout(new GridLayout(4, 3));
		panelNumeros.setBorder(new EmptyBorder(4, 4, 4, 4));

		for (int n = 9; n >= 0; n--) {
			nuevoBotonNumerico("" + n);
		}

		nuevoBotonNumerico(".");

		panel.add("Center", panelNumeros);

		panelOperaciones = new JPanel();
		panelOperaciones.setLayout(new GridLayout(6, 1));
		panelOperaciones.setBorder(new EmptyBorder(4, 4, 4, 4));

		nuevoBotonOperacion("+");
		nuevoBotonOperacion("-");
		nuevoBotonOperacion("*");
		nuevoBotonOperacion("/");
		nuevoBotonOperacion("Sen");
		nuevoBotonOperacion("Cos");
                //Se agregan los botones 
                nuevoBotonOperacion("^");
                nuevoBotonOperacion("%");
                nuevoBotonOperacion("!");
                nuevoBotonOperacion("=");
                nuevoBotonOperacion("CE");

		panel.add("East", panelOperaciones);

		validate();
	}

	/**
	 * Crea un boton del teclado numérico y enlaza sus eventos con el listener
	 * correspondiente
	 * 
	 * @param digito
	 *            boton a crear
	 */
	private void nuevoBotonNumerico(String digito) {
		JButton btn = new JButton();
		btn.setText(digito);
		btn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				JButton btn = (JButton) evt.getSource();
				numeroPulsado(btn.getText());
			}
		});

		panelNumeros.add(btn);
	}

	/**
	 * Crea un botón de operacion y lo enlaza con sus eventos.
	 * 
	 * @param operacion
	 */
	private void nuevoBotonOperacion(String operacion) {
		JButton btn = new JButton(operacion);
		btn.setForeground(Color.RED);

		btn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				JButton btn = (JButton) evt.getSource();
				operacionPulsado(btn.getText());
			}
		});

		panelOperaciones.add(btn);
	}

	/**
	 * Gestiona las pulsaciones de teclas numéricas
	 * 
	 * @param digito
	 *            tecla pulsada
	 */
	private void numeroPulsado(String digito) {
		if (pantalla.getText().equals("0") || nuevaOperacion) {
			pantalla.setText(digito);
		} else {
			pantalla.setText(pantalla.getText() + digito);
		}
		nuevaOperacion = false;
	}

	/**
	 * Gestiona el gestiona las pulsaciones de teclas de operación
	 * 
	 * @param tecla
	 */
	private void operacionPulsado(String tecla) {
            //Valida operaciones que solo necesitan un número
		if (tecla.equals("=") || tecla.equals("Sen") || tecla.equals("Cos") || tecla.equals("!")) {
                    switch(tecla)
                    {
                        case "Sen":
                            operacion = "Sen";
                            break;
                        case "Cos":
                            operacion = "Cos";
                            break;
                        case "!":
                            operacion = "!";
                            break;
                    }
			calcularResultado();
		} else if (tecla.equals("CE")) {
			resultado = 0;
			pantalla.setText("");
			nuevaOperacion = true;
		} else {
			operacion = tecla;
			if ((resultado > 0) && !nuevaOperacion) {
				calcularResultado();
			} else {
				resultado = new Double(pantalla.getText());
			}
		}

		nuevaOperacion = true;
	}

	/**
	 * Calcula el resultado y lo muestra por pantalla
	 */
	private void calcularResultado() {
            switch (operacion) {
                case "+":
                    resultado += new Double(pantalla.getText());
                    break;
                case "-":
                    resultado -= new Double(pantalla.getText());
                    break;
                case "/":
                    resultado /= new Double(pantalla.getText());
                    break;
                case "*":
                    resultado *= new Double(pantalla.getText());
                    break;
                //Agregamos 5 operaciones más.
                case "%":
                    resultado = mod(resultado, new Double(pantalla.getText()));
                    break;
                case "Sen":
                    resultado = seno(new Double(pantalla.getText()));
                    break;
                case "Cos":
                    resultado = coseno(new Double(pantalla.getText()));
                    break;
                case "^":
                    resultado = potencia(resultado, new Double(pantalla.getText()));
                    break;
                case "!":
                    resultado = factorial(new Double(pantalla.getText()));
                    break;
            }

		pantalla.setText("" + resultado);
		operacion = "";
	}
        //Agregamos los métodos para realizar las 5 operarciones agregadas.
        public double mod(double num, double num2)
	{
		double res;
		res = num % num2;
		return res;
	}

	public double seno(double num)
	{
		double res = Math.sin(Math.toRadians(num));
		return res;
	}

	public double coseno(double num)
	{
		double res = Math.cos(Math.toRadians(num));
		return res;
	}

	public double potencia(double num, double num2)
	{
		double res = num;
		for(int i = 1; i<num2;i++)
		{
			res = res*num;
		}
		return res;
	}

	public double factorial(double num)
	{
		double res = 1;
		for(int i = 1; i<=num; i++)
		{
			res = res*i;
		}
		return res;
	}
}
