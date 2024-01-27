package ec.edu.puce.elecciones.formulario;

import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import ec.edu.puce.elecciones.dominio.Ciudad;
import ec.edu.puce.elecciones.dominio.Prefecto;
import ec.edu.puce.elecciones.dominio.Provincia;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ReportePorCiudad extends JInternalFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel model;

	private List<Prefecto> prefectos;
	private List<Provincia> provincias;
	private String[] provinciasTexto;
	private String[] cantonesTexto;
	private JButton btnCancelar;
	private JLabel lblNombres;
	private JComboBox comboBox;
	private JLabel lblGanador;
	private JLabel lblCiudad;
	private JComboBox comboBox_1;

	public ReportePorCiudad(List<Prefecto> prefectos, List<Provincia> provincias) {
		this.prefectos = prefectos;
		this.provincias = provincias;
		this.provinciasTexto = new String[this.provincias.size()];
		int i = 0;
		for (Provincia provincia : this.provincias) {
			this.provinciasTexto[i] = provincia.getNombre();
			i++;
		}
		
		setTitle("REPORTE GENERAL POR PROVINCIA");
		setBounds(100, 100, 600, 309);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 71, 566, 167);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(model.getValueAt(0, 0));
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Votos"
			}
		));
		scrollPane.setViewportView(table);
		model = (DefaultTableModel) table.getModel();
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(235, 244, 117, 25);
		getContentPane().add(btnCancelar);
		
		lblNombres = new JLabel("Provincia");
		lblNombres.setBounds(12, 21, 70, 15);
		getContentPane().add(lblNombres);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llenarTabla();
				seleccionarProvincia();
				calcularGanador();
				
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(this.provinciasTexto));
		comboBox.setBounds(79, 12, 231, 24);
		getContentPane().add(comboBox);
		
		lblGanador = new JLabel("Ganador: ");
		lblGanador.setBounds(385, 21, 193, 14);
		getContentPane().add(lblGanador);
		
		lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(12, 47, 70, 15);
		getContentPane().add(lblCiudad);
		this.cantonesTexto = new String[this.provincias.get(comboBox.getSelectedIndex()).getCiudades().size()];
		int j = 0;
		for (Ciudad ciudad : this.provincias.get(comboBox.getSelectedIndex()).getCiudades()) {
			this.cantonesTexto[j] = ciudad.getCiudad();
			j++;
		}
		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llenarTabla();
				calcularGanador();
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(this.cantonesTexto));
		comboBox_1.setSelectedIndex(0);
		comboBox_1.setBounds(79, 43, 231, 24);
		getContentPane().add(comboBox_1);

		JButton btnBarras = new JButton("Barras");
		btnBarras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearResultadosEnBarras();
			}
		
		});
		btnBarras.setBounds(101, 245, 89, 23);
		getContentPane().add(btnBarras);
		
		JButton btnPastal = new JButton("Pastel");
		btnPastal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearResultadosEnPastel();
			}
		});
		btnPastal.setBounds(424, 245, 89, 23);
		getContentPane().add(btnPastal);
		
		
		llenarTabla();
		if(prefectos != null) {
			calcularGanador();
		}
	}

	
	private void llenarTabla() {
		model.setRowCount(0);
		for (Prefecto prefecto : prefectos) {
			if(prefecto.getProvincia().getNombre() == this.provinciasTexto[this.comboBox.getSelectedIndex()]) {
				Object[] fila = new Object[2];
				fila[0] = prefecto.getNombre();
				if(!(provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).getVotosCandidatos().isEmpty())) {
					fila[1] = provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).getVotosCandidatos().get(prefecto.getId()-1).votosCandidatoCiudad();;
				} else {
					fila[1] = 0;
				}
				
				model.addRow(fila);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancelar) {
			dispose();
		}
		for (Prefecto prefecto : prefectos) {
			if (prefecto.getNombre() == e.getActionCommand()) {
				prefecto.setVotos(prefecto.getVotos() + 1);
				llenarTabla();
			}
		}
	}
	
	public void seleccionarProvincia() {
		this.cantonesTexto = new String[this.provincias.get(comboBox.getSelectedIndex()).getCiudades().size()];
		int j = 0;
		for (Ciudad ciudad : this.provincias.get(comboBox.getSelectedIndex()).getCiudades()) {
			this.cantonesTexto[j] = ciudad.getCiudad();
			j++;
		}
		comboBox_1.setModel(new DefaultComboBoxModel(cantonesTexto));
	}
	
	public List<Prefecto> getPrefectos() {
		return prefectos;
	}

	public void setPrefectos(List<Prefecto> prefectos) {
		this.prefectos = prefectos;
	}
	
	public void calcularGanador() {
		int mayor = 0;
		for(Prefecto prefecto : prefectos) {
			int votosLocales = 0;
			if(!(provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).getVotosCandidatos().isEmpty())) {
				votosLocales = provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).getVotosCandidatos().get(prefecto.getId()-1).votosCandidatoCiudad();
			} 
			if(votosLocales>mayor && prefecto.getProvincia().getNombre()==provincias.get(this.comboBox.getSelectedIndex()).getNombre()) {
				mayor = votosLocales;
				this.lblGanador.setText("Ganador: "+prefecto.getNombre());
			}
		}
	}
	
	private void crearResultadosEnBarras() {
		final JInternalFrame frame = new JInternalFrame("Resultado en Barras", true);

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Prefecto prefecto : prefectos) {
			if(prefecto.getProvincia().getNombre()==provincias.get(this.comboBox.getSelectedIndex()).getNombre()) {
				dataset.addValue(provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).getVotosCandidatos().get(prefecto.getId()-1).votosCandidatoCiudad(), "Votos Provinciales", prefecto.getNombre());
			}
		}
		final JFreeChart chart = ChartFactory.createBarChart("Bar Chart", "Category", "Series", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(600, 400));

		final JPanel panel = new JPanel();
		panel.setBounds(0, 0, 600, 400);
		panel.setLayout(new BorderLayout());
		panel.add(chartPanel);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		
		

		panel.add(btnCancelar, BorderLayout.SOUTH);
		frame.getContentPane().add(panel);
		this.getDesktopPane().add(frame);
		frame.pack();
		frame.setVisible(true);
	}

	private void crearResultadosEnPastel() {
		final JInternalFrame frame = new JInternalFrame("Resultado en Pastel", true);

		DefaultPieDataset datos = new DefaultPieDataset();
		for (Prefecto prefecto : prefectos) {
			if(prefecto.getProvincia().getNombre()==provincias.get(this.comboBox.getSelectedIndex()).getNombre()) {
				datos.setValue(prefecto.getNombre(), provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).getVotosCandidatos().get(prefecto.getId()-1).votosCandidatoCiudad());
			}
		}

		JFreeChart grafico = ChartFactory.createPieChart("Votos Provinciales", datos);
		ChartPanel chartPanel = new ChartPanel(grafico);
		chartPanel.setBounds(10, 50, 450, 350);

		final JPanel panel = new JPanel();
		panel.setBounds(0, 0, 600, 400);
		panel.setLayout(new BorderLayout());
		panel.add(chartPanel);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});

		panel.add(btnCancelar, BorderLayout.SOUTH);
		frame.getContentPane().add(panel);
		this.getDesktopPane().add(frame);
		frame.pack();
		frame.setVisible(true);
	}
}
