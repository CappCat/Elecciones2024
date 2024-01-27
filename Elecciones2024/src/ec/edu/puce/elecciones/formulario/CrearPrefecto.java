package ec.edu.puce.elecciones.formulario;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ec.edu.puce.elecciones.dominio.Prefecto;
import ec.edu.puce.elecciones.dominio.Provincia;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class CrearPrefecto extends JInternalFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;

	private Prefecto prefecto;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private String[] nombresProvincias = {"Azuay", "Cañar", "Pichincha", "Manabí", "Guayas"};

	private List<Provincia> provincias;
	private List<Prefecto> prefectos;
	private int idPrefecto;
	private JComboBox comboBox;
	
	public CrearPrefecto(List<Prefecto> prefectos, List<Provincia> provincias) {
		idPrefecto=1;
		this.prefectos=prefectos;
		this.provincias = provincias;
		setTitle("CANDIDATOS A PREFECTO");
		setBounds(100, 100, 443, 385);
		getContentPane().setLayout(null);
		
		JLabel lblNombres = new JLabel("Nombres");
		lblNombres.setBounds(33, 43, 70, 15);
		getContentPane().add(lblNombres);

		txtNombre = new JTextField();
		txtNombre.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
			}
		});
		txtNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				agregarPrefecto();
			}
		});
		txtNombre.setBounds(100, 41, 231, 19);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(this);
		btnNuevo.setBounds(30, 72, 117, 25);
		getContentPane().add(btnNuevo);

		btnGuardar = new JButton("Agregar");
		btnGuardar.addActionListener(this);
		btnGuardar.setBounds(157, 72, 117, 25);
		getContentPane().add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(286, 72, 117, 25);
		getContentPane().add(btnCancelar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 115, 416, 224);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(model.getValueAt(0, 0));
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nombre" }));
		scrollPane.setViewportView(table);
		
		JLabel lblNombres_1 = new JLabel("Provincia");
		lblNombres_1.setBounds(33, 14, 70, 15);
		getContentPane().add(lblNombres_1);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarFilas();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(nombresProvincias));
		comboBox.setBounds(100, 5, 231, 24);
		getContentPane().add(comboBox);

		model = (DefaultTableModel) table.getModel();
		agregarFila();
	}

	private void nuevo() {
		prefecto = new Prefecto();
		txtNombre.setText(prefecto.getNombre());
	}
	
	private void cambiarFilas() {
		model.setRowCount(0);
		/*if(this.comboBox.getSelectedIndex()<0) {
			return;
		}*/
		for (Prefecto prefecto : prefectos) {
			if(prefecto.getProvincia().getNombre() == provincias.get(this.comboBox.getSelectedIndex()).getNombre()) {
				Object[] fila = new Object[1];
				fila[0] = prefecto.getNombre();
				model.addRow(fila);
			}
		}
	}

	private void agregarPrefecto() {
		prefecto = new Prefecto();
		prefecto.setId(idPrefecto);
		prefecto.setNombre(txtNombre.getText());
		prefecto.setProvincia(provincias.get(this.comboBox.getSelectedIndex()));
		prefectos.add(prefecto);
		agregarFila();
		cambiarFilas();
		txtNombre.setText("");
		idPrefecto++;
		
	}

	private void agregarFila() {
		model.setRowCount(0);
		for (Prefecto prefecto : prefectos) {
			Object[] fila = new Object[1];
			fila[0] = prefecto.getNombre();
			model.addRow(fila);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNuevo) {
			nuevo();
		} else if (e.getSource() == btnGuardar) {
			agregarPrefecto();
		} else if (e.getSource() == btnCancelar) {
			dispose();
		}
		else if (e.getSource() == txtNombre) {
			agregarPrefecto();
		}
	}

	public List<Prefecto> getPrefectos() {
		return prefectos;
	}

	public void setPrefectos(List<Prefecto> prefectos) {
		this.prefectos = prefectos;
	}
	

}
