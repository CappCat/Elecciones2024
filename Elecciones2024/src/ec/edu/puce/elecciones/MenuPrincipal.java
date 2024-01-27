package ec.edu.puce.elecciones;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import ec.edu.puce.elecciones.dominio.Ciudad;
import ec.edu.puce.elecciones.dominio.Prefecto;
import ec.edu.puce.elecciones.dominio.Provincia;
import ec.edu.puce.elecciones.formulario.BocaDeUrna;
import ec.edu.puce.elecciones.formulario.CrearPrefecto;
import ec.edu.puce.elecciones.formulario.ReporteGeneral;
import ec.edu.puce.elecciones.formulario.ReportePorCiudad;

import javax.swing.JMenuBar;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDesktopPane;

import java.awt.BorderLayout;
import java.awt.CardLayout;

public class MenuPrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contenedor;
	private JDesktopPane desktopPane;
	private JMenuItem mntmSalir;
	private JMenuItem mntmCrearPrefectos;
	private JMenuItem mntmBocaDeUrna;

	public List<Prefecto> prefectos = new ArrayList<>();
	public List<Provincia> provincias = new ArrayList<Provincia>();
	public String[] nombresProvincias = {"Azuay", "Cañar", "Pichincha", "Manabí", "Guayas"};
	public String[] cantonesAzuay = {"Cuenca", "El Pan","Girón","Guachapala","Gualaceo"};
	public String[] cantonesCañar = {"Azogues", "Cañar", "Biblián", "Suscal", "La Troncal"};
	public String[] cantonesPichincha = {"Quito", "Cayambe","Rumiñahui","Mejía","Pedro Moncayo"};
	public String[] cantonesManabi = {"Chone","Portoviejo","Manta","Rocafuerte","Tosagua"};
	public String[] cantonesGuayas = {"Guayaquil", "Daule","Durán","El Empalme","Balao"};
	private JMenuItem mntmResultadosBarras;
	private JMenuItem mntmResultadosPastel;
	private JMenuItem mntmResultadosPorProvincia;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuPrincipal() {
		
		
		for (String provincia: nombresProvincias) {
			Provincia provinciaE = new Provincia(provincia, prefectos);
			provincias.add(provinciaE);
			if (provinciaE.getNombre() == "Azuay") {
				for (String ciudad : cantonesAzuay) {
					provinciaE.getCiudades().add(new Ciudad(ciudad, provinciaE));
				}
				continue;
			} else if (provinciaE.getNombre() == "Cañar") {
				for (String ciudad : cantonesCañar) {
					provinciaE.getCiudades().add(new Ciudad(ciudad, provinciaE));
				}
				continue;
			} else if (provinciaE.getNombre() == "Pichincha") {
				for (String ciudad : cantonesPichincha) {
					provinciaE.getCiudades().add(new Ciudad(ciudad, provinciaE));
				}
				continue;
			} else if (provinciaE.getNombre() == "Manabí") {
				for (String ciudad : cantonesManabi) {
					provinciaE.getCiudades().add(new Ciudad(ciudad, provinciaE));
				}
				continue;
			} else if (provinciaE.getNombre() == "Guayas") {
				for (String ciudad : cantonesGuayas) {
					provinciaE.getCiudades().add(new Ciudad(ciudad, provinciaE));
				}
				continue;
			}
		}
		setTitle("SISTEMA CONTEO DE VOTOS BOCA DE URNA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(192, 191, 188));
		setJMenuBar(menuBar);

		JMenu mnSistema = new JMenu("Sistema");
		mnSistema.setFont(new Font("Dialog", Font.BOLD, 16));
		menuBar.add(mnSistema);

		mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(this);
		mntmSalir.setFont(new Font("Dialog", Font.BOLD, 16));
		mnSistema.add(mntmSalir);

		JMenu mnAdministracin = new JMenu("Administración");
		mnAdministracin.setFont(new Font("Dialog", Font.BOLD, 16));
		menuBar.add(mnAdministracin);

		mntmCrearPrefectos = new JMenuItem("Crear Prefectos");
		mntmCrearPrefectos.setFont(new Font("Dialog", Font.BOLD, 16));
		mntmCrearPrefectos.addActionListener(this);
		mnAdministracin.add(mntmCrearPrefectos);

		JMenu mnProceso = new JMenu("Proceso");
		mnProceso.setFont(new Font("Dialog", Font.BOLD, 16));
		menuBar.add(mnProceso);

		mntmBocaDeUrna = new JMenuItem("Boca de Urna");
		mntmBocaDeUrna.setFont(new Font("Dialog", Font.BOLD, 16));
		mntmBocaDeUrna.addActionListener(this);
		mnProceso.add(mntmBocaDeUrna);

		JMenu mnReportes = new JMenu("Reportes");
		mnReportes.setFont(new Font("Dialog", Font.BOLD, 16));
		menuBar.add(mnReportes);

		mntmResultadosPorProvincia = new JMenuItem("Resultados por provincia (o generales)");
		mntmResultadosPorProvincia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReporteGeneral rg = new ReporteGeneral(prefectos, provincias);
				desktopPane.add(rg);
				rg.setVisible(true);
			}
		});
		mntmResultadosPorProvincia.setFont(new Font("Dialog", Font.BOLD, 16));
		mnReportes.add(mntmResultadosPorProvincia);

		JMenuItem mntmResultadosPorCantn = new JMenuItem("Resultados por cantón o ciudad");
		mntmResultadosPorCantn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReportePorCiudad rc = new ReportePorCiudad(prefectos, provincias);
				desktopPane.add(rc);
				rc.setVisible(true);
			}
		});
		mntmResultadosPorCantn.setFont(new Font("Dialog", Font.BOLD, 16));
		mnReportes.add(mntmResultadosPorCantn);

		JMenu mnGrficos = new JMenu("Gráficos");
		mnGrficos.setFont(new Font("Dialog", Font.BOLD, 16));
		menuBar.add(mnGrficos);

		mntmResultadosBarras = new JMenuItem("Resultados Barras");
		mnGrficos.add(mntmResultadosBarras);
		mntmResultadosBarras.addActionListener(this);
		mntmResultadosBarras.setFont(new Font("Dialog", Font.BOLD, 16));

		mntmResultadosPastel = new JMenuItem("Resultados Pastel");
		mnGrficos.add(mntmResultadosPastel);
		mntmResultadosPastel.addActionListener(this);
		mntmResultadosPastel.setFont(new Font("Dialog", Font.BOLD, 16));
		contenedor = new JPanel();
		contenedor.setBackground(new Color(255, 255, 255));
		contenedor.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contenedor);
		contenedor.setLayout(new CardLayout(0, 0));

		desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(255, 255, 255));
		contenedor.add(desktopPane, "name_35522358088801");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmSalir) {
			dispose();
		} else if (e.getSource() == mntmCrearPrefectos) {
			CrearPrefecto crearPrefecto = new CrearPrefecto(prefectos, provincias);
			desktopPane.add(crearPrefecto);
			crearPrefecto.setVisible(true);

		} else if (e.getSource() == mntmBocaDeUrna) {
			BocaDeUrna bocaUrna = new BocaDeUrna(prefectos, provincias);
			desktopPane.add(bocaUrna);
			bocaUrna.setVisible(true);
		} else if (e.getSource() == mntmResultadosBarras) {
			crearResultadosEnBarras();
		} else if (e.getSource() == mntmResultadosPastel) {
			crearResultadosEnPastel();
		} 

	}

	private void crearResultadosEnBarras() {
		final JInternalFrame frame = new JInternalFrame("Resultado en Barras", true);

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Prefecto prefecto : prefectos) {
			dataset.addValue(prefecto.getVotos(), "Prefecto", prefecto.getNombre());
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
		desktopPane.add(frame);
		frame.pack();
		frame.setVisible(true);
	}

	private void crearResultadosEnPastel() {
		final JInternalFrame frame = new JInternalFrame("Resultado en Pastel", true);

		DefaultPieDataset datos = new DefaultPieDataset();
		for (Prefecto prefecto : prefectos) {
			datos.setValue(prefecto.getNombre(), prefecto.getVotos());
		}

		JFreeChart grafico = ChartFactory.createPieChart("Prefectos", datos);
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
		desktopPane.add(frame);
		frame.pack();
		frame.setVisible(true);
	}
}
