package ec.edu.puce.elecciones.formulario;

import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ec.edu.puce.elecciones.dominio.Ciudad;
import ec.edu.puce.elecciones.dominio.Prefecto;
import ec.edu.puce.elecciones.dominio.Provincia;
import ec.edu.puce.elecciones.dominio.VotosCiudad;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ScrollPaneConstants;

public class BocaDeUrna extends JInternalFrame implements ActionListener {
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
	private JComboBox comboBox_1;
	private JLabel lblCiudad;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane;
	private JPanel panel;

	public BocaDeUrna(List<Prefecto> prefectos, List<Provincia> provincias) {
		this.prefectos = prefectos;
		this.provincias = provincias;
		this.provinciasTexto = new String[this.provincias.size()];
		setTitle("BOCA DE URNA - REGISTRO");
		setBounds(100, 100, 600, 427);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 172, 566, 167);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(model.getValueAt(0, 0));
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nombre", "Votos" }));
		scrollPane.setViewportView(table);
		model = (DefaultTableModel) table.getModel();

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(157, 351, 117, 25);
		getContentPane().add(btnCancelar);
		
		lblNombres = new JLabel("Provincia");
		lblNombres.setBounds(12, 21, 70, 15);
		getContentPane().add(lblNombres);
		int i = 0;
		for (Provincia provincia : this.provincias) {
			this.provinciasTexto[i] = provincia.getNombre();
			i++;
		}
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seleccionarProvincia();
				cargarCandidatos();
				llenarTabla();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(provinciasTexto));
		comboBox.setBounds(79, 12, 231, 24);
		getContentPane().add(comboBox);
		this.cantonesTexto = new String[this.provincias.get(comboBox.getSelectedIndex()).getCiudades().size()];
		int j = 0;
		for (Ciudad ciudad : this.provincias.get(comboBox.getSelectedIndex()).getCiudades()) {
			this.cantonesTexto[j] = ciudad.getCiudad();
			j++;
		}
		
		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearVotosCiudad();
				llenarTabla();
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(cantonesTexto));
		comboBox_1.setSelectedIndex(0);
		comboBox_1.setBounds(79, 43, 231, 24);
		getContentPane().add(comboBox_1);
		
		lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(12, 48, 70, 15);
		getContentPane().add(lblCiudad);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(12, 76, 566, 91);
		getContentPane().add(scrollPane_1);
		
		panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		panel.setLayout(null);

		
		cargarCandidatos();
		llenarTabla();
		crearVotosCiudad();
	}

	private void cargarCandidatos() {
		panel.removeAll();
		int x = 0;
		int y = 0;
		for (Prefecto prefecto : prefectos) {
			if (prefecto.getProvincia().getNombre() == this.provincias.get(this.comboBox.getSelectedIndex()).getNombre()) {
				JButton btnPrefecto = new JButton(prefecto.getNombre());
				btnPrefecto.setBounds(x * 105, y*30, 100, 25);
				btnPrefecto.addActionListener(this);
				panel.setSize(panel.getWidth()+155, panel.getHeight());
				panel.setLayout(null);
				panel.add(btnPrefecto);
				x++;
			}
			if(x%5==0 && x!=0) {
				x=0;
				y++;
			}
		}
	}

	private void llenarTabla() {
		model.setRowCount(0);
		for (Prefecto prefecto : prefectos) {
			if (prefecto.getProvincia().getNombre() == this.provincias.get(this.comboBox.getSelectedIndex()).getNombre()) {
				Object[] fila = new Object[2];
				fila[0] = prefecto.getNombre();
				fila[1] = provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).getVotosCandidatos().get(prefecto.getId()-1).votosCandidatoCiudad();
				model.addRow(fila);
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
		crearVotosCiudad();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancelar) {
			dispose();
		}
		for (Prefecto prefecto : prefectos) {
			VotosCiudad votosciudad = new VotosCiudad(prefecto);
			if (!(provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).getVotosCandidatos().contains(votosciudad))) {
				provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).agregarCandidato(votosciudad);
			}
			
			if (prefecto.getNombre() == e.getActionCommand()) {
				provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).getVotosCandidatos().get(prefecto.getId()-1).aumentarVotos();
				prefecto.setVotos(prefecto.getVotos() + 1);
				llenarTabla();
			}
		}
	}
	
	public void crearVotosCiudad() {
		for (Prefecto prefecto : prefectos) {
			VotosCiudad votosciudad = new VotosCiudad(prefecto);
			provincias.get(this.comboBox.getSelectedIndex()).getCiudades().get(this.comboBox_1.getSelectedIndex()).agregarCandidato(votosciudad);
		}
	}
	
	

	public List<Prefecto> getPrefectos() {
		return prefectos;
	}

	public void setPrefectos(List<Prefecto> prefectos) {
		this.prefectos = prefectos;
	}
}
