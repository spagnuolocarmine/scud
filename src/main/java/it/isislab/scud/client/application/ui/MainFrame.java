package it.isislab.scud.client.application.ui;

import it.isislab.scud.client.application.ui.newsimulation.NewDomain;
import it.isislab.scud.client.application.ui.newsimulation.NewInputOutput;
import it.isislab.scud.client.application.ui.newsimulation.NewSimulationPanel;
import it.isislab.scud.client.application.ui.tabwithclose.JTabbedPaneWithCloseIcons;
import it.isislab.scud.client.application.ui.tabwithclose.ProgressbarDialog;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Loop;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulations;
import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.input.Input;
import it.isislab.scud.core.model.parameters.xsd.output.Output;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.hadoop.hdfs.web.HftpFileSystem;
import org.apache.log4j.Logger;

import scala.xml.dtd.DFAContentModel;

/**
 * @author Carmine Spagnuolo
 */
public class MainFrame extends JFrame {
	protected final Controller controller; 
	private static final long serialVersionUID = 1L;
	public MainFrame(Controller controller) {
		this.controller=controller;
		initComponents();
	}

	private void initComponents() {
		initFileSystem();


		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuItem1 = new JMenuItem();
		BackgroundPanel = new JPanel();
		ButtonPanel = new JPanel();
		buttonReload = new JButton();
		buttonAdd = new JButton();
		buttonExport = new JButton();
		buttonStop = new JButton();
		buttonSubmit = new JButton();
		MainPanel = new JPanel();
		LeftPanel = new JPanel();
		scrollPane1 = new JScrollPane();

		CenterPanel = new JPanel();
		splitPane1 = new JSplitPane();
		Toppanel = new JPanel();
		CenterTabbedscrollPane = new JScrollPane();
		CentraltabbedPane = new JTabbedPaneWithCloseIcons();
		TestCentralTabbedpanel = new JPanel();
		Bottonpanel = new JPanel();
		CenterBottomscrollPane = new JScrollPane();
		CenterBottomlist = new JList();

		//======== this ========
		setTitle("SCUD Client");
		Container contentPane = getContentPane();

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("SCUD Client");

				//---- menuItem1 ----
				menuItem1.setText("About");
				menu1.add(menuItem1);
			}
			menuBar1.add(menu1);
		}
		setJMenuBar(menuBar1);

		//======== BackgroundPanel ========
		{


			//======== ButtonPanel ========
			{
				ButtonPanel.setBorder(null);


				buttonReload.setIcon(new ImageIcon("scud-resources/images/ic_action_refresh.png"));
				buttonReload.setToolTipText("Reload. Reloads the simulations from HDFS.");

				buttonAdd.setIcon(new ImageIcon("scud-resources/images/ic_action_new.png"));
				buttonAdd.setToolTipText("Add. Creates a new child node.");

				buttonSubmit.setIcon(new ImageIcon("scud-resources/images/ic_action_play.png"));
				buttonSubmit.setToolTipText("Submit. Submits the selected simulation to the system.");

				buttonStop.setIcon(new ImageIcon("scud-resources/images/ic_action_stop.png"));
				buttonStop.setToolTipText("Stop. Interrupts the selected simulation.");

				buttonExport.setIcon(new ImageIcon("scud-resources/images/ic_action_download.png"));
				buttonExport.setToolTipText("Download. Downloads the simulation package from HDFS.");

				buttonReload.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonReloadActionPerformed(e);
					}
				});

				buttonAdd.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonAddActionPerformed(e);
					}
				});

				buttonExport.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonExportActionPerformed(e);
					}
				});

				buttonStop.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonStopActionPerformed(e);
					}
				});
				buttonSubmit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonSubmitActionPerformed(e);
					}
				});


				GroupLayout ButtonPanelLayout = new GroupLayout(ButtonPanel);
				ButtonPanel.setLayout(ButtonPanelLayout);
				ButtonPanelLayout.setHorizontalGroup(
						ButtonPanelLayout.createParallelGroup()
						.addGroup(ButtonPanelLayout.createSequentialGroup()
								.addComponent(buttonReload)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(buttonAdd)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(buttonSubmit)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(buttonStop)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(buttonExport)
								.addGap(0, 0, Short.MAX_VALUE))
						);
				ButtonPanelLayout.setVerticalGroup(
						ButtonPanelLayout.createParallelGroup()
						.addGroup(ButtonPanelLayout.createSequentialGroup()
								.addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(buttonReload)
										.addComponent(buttonAdd)
										.addComponent(buttonSubmit)
										.addComponent(buttonStop)
										.addComponent(buttonExport))
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						);
			}

			//======== MainPanel ========
			{

				//======== LeftPanel ========
				{
					LeftPanel.setBorder(new TitledBorder("File System"));

					//======== scrollPane1 ========
					{
						scrollPane1.setViewportView(tree1);
					}

					GroupLayout LeftPanelLayout = new GroupLayout(LeftPanel);
					LeftPanel.setLayout(LeftPanelLayout);
					LeftPanelLayout.setHorizontalGroup(
							LeftPanelLayout.createParallelGroup()
							.addGroup(LeftPanelLayout.createSequentialGroup()
									.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))
							);
					LeftPanelLayout.setVerticalGroup(
							LeftPanelLayout.createParallelGroup()
							.addComponent(scrollPane1)
							);
				}

				//======== CenterPanel ========
				{

					//======== splitPane1 ========
					{
						splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);

						//======== Toppanel ========
						{
							Toppanel.setBackground(new Color(255, 153, 153));
							//======== CenterTabbedscrollPane ========
							{

								//======== CentraltabbedPane ========
								{

									//======== TestCentralTabbedpanel ========
									{

										GroupLayout TestCentralTabbedpanelLayout = new GroupLayout(TestCentralTabbedpanel);
										TestCentralTabbedpanel.setLayout(TestCentralTabbedpanelLayout);
										TestCentralTabbedpanelLayout.setHorizontalGroup(
												TestCentralTabbedpanelLayout.createParallelGroup()
												.addGap(0, 1072, Short.MAX_VALUE)
												);
										TestCentralTabbedpanelLayout.setVerticalGroup(
												TestCentralTabbedpanelLayout.createParallelGroup()
												.addGap(0, 50, Short.MAX_VALUE)
												);
									}
									//									CentraltabbedPane.addTab("Test 01", TestCentralTabbedpanel);
									CentraltabbedPane.addTab("Test 01", new NewSimulationPanel(""));
									CentraltabbedPane.addTab("Test 02", new NewDomain());
									CentraltabbedPane.addTab("Test 03", new NewInputOutput());
									CentraltabbedPane.addTab("Test 04", new XMLPanel());
								}
								CenterTabbedscrollPane.setViewportView(CentraltabbedPane);
							}

							GroupLayout ToppanelLayout = new GroupLayout(Toppanel);
							Toppanel.setLayout(ToppanelLayout);
							ToppanelLayout.setHorizontalGroup(
									ToppanelLayout.createParallelGroup()
									.addComponent(CenterTabbedscrollPane)
									);
							ToppanelLayout.setVerticalGroup(
									ToppanelLayout.createParallelGroup()
									.addGroup(ToppanelLayout.createSequentialGroup()
											.addComponent(CenterTabbedscrollPane)
											.addGap(0, 0, 0))
									);
						}
						splitPane1.setTopComponent(Toppanel);

						//======== Bottonpanel ========
						{
							Bottonpanel.setBackground(new Color(51, 51, 255));

							//======== CenterBottomscrollPane ========
							{
								CenterBottomscrollPane.setViewportView(CenterBottomlist);
							}

							GroupLayout BottonpanelLayout = new GroupLayout(Bottonpanel);
							Bottonpanel.setLayout(BottonpanelLayout);
							BottonpanelLayout.setHorizontalGroup(
									BottonpanelLayout.createParallelGroup()
									.addComponent(CenterBottomscrollPane, GroupLayout.DEFAULT_SIZE, 1097, Short.MAX_VALUE)
									);
							BottonpanelLayout.setVerticalGroup(
									BottonpanelLayout.createParallelGroup()
									.addComponent(CenterBottomscrollPane, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
									);
						}
						splitPane1.setBottomComponent(Bottonpanel);
					}

					GroupLayout CenterPanelLayout = new GroupLayout(CenterPanel);
					CenterPanel.setLayout(CenterPanelLayout);
					CenterPanelLayout.setHorizontalGroup(
							CenterPanelLayout.createParallelGroup()
							.addGroup(CenterPanelLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(splitPane1)
									.addContainerGap())
							);
					CenterPanelLayout.setVerticalGroup(
							CenterPanelLayout.createParallelGroup()
							.addGroup(CenterPanelLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(splitPane1)
									.addContainerGap())
							);
				}

				GroupLayout MainPanelLayout = new GroupLayout(MainPanel);
				MainPanel.setLayout(MainPanelLayout);
				MainPanelLayout.setHorizontalGroup(
						MainPanelLayout.createParallelGroup()
						.addGroup(MainPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(LeftPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(CenterPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
						);
				MainPanelLayout.setVerticalGroup(
						MainPanelLayout.createParallelGroup()
						.addComponent(CenterPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(MainPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(LeftPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
						);
			}

			GroupLayout BackgroundPanelLayout = new GroupLayout(BackgroundPanel);
			BackgroundPanel.setLayout(BackgroundPanelLayout);
			BackgroundPanelLayout.setHorizontalGroup(
					BackgroundPanelLayout.createParallelGroup()
					.addComponent(ButtonPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(BackgroundPanelLayout.createSequentialGroup()
							.addComponent(MainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(12, 12, 12))
					);
			BackgroundPanelLayout.setVerticalGroup(
					BackgroundPanelLayout.createParallelGroup()
					.addGroup(BackgroundPanelLayout.createSequentialGroup()
							.addComponent(ButtonPanel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(MainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())
					);
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
				contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(BackgroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
				);
		contentPaneLayout.setVerticalGroup(
				contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
						.addComponent(BackgroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(31, 31, 31))
				);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		splitPane1.setDividerLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.8));
		splitPane1.setOneTouchExpandable(true);
		splitPane1.setContinuousLayout(true);


		updateFileSystem();
		tree1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				doMouseClicked(me);
			}
		});

		pack();
		setLocationRelativeTo(null);
	}

	protected void buttonSubmitActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	protected void buttonStopActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	protected void buttonExportActionPerformed(ActionEvent e) {
		chooser = new JFileChooser();
		chooser.setDialogTitle("Select your donwload directory");
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			DefaultMutableTreeNode selected =(DefaultMutableTreeNode)tree1.getLastSelectedPathComponent();
			if(selected !=null && selected.toString().contains("Simulation name")){
				final ProgressbarDialog pd=new ProgressbarDialog(this);
				pd.setNoteMessage("Please wait.");
				pd.setVisible(true);
				final JProgressBar bar=pd.getProgressBar1();
				pd.setTitleMessage("Download simulation in: "+chooser.getSelectedFile().getAbsolutePath());
				bar.setIndeterminate(true);
				
				Enumeration<DefaultMutableTreeNode> children = selected.children();
				while(children.hasMoreElements()){
					DefaultMutableTreeNode node = children.nextElement();
					if(node.toString().contains("Id")){
						String[] split = node.toString().split(":");
						final String idSim = split[1].trim();
						class MyTaskConnect extends Thread {

							public void run(){

								controller.getresult(idSim,chooser.getSelectedFile().getAbsolutePath());
								bar.setIndeterminate(false);

								pd.setVisible(false);
							}
						}
						(new MyTaskConnect()).start();
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(this,"Selection error!\n You must select selection node from SCUD filesystem");
			}
		}

	}


	protected void buttonAddActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	protected void buttonReloadActionPerformed(ActionEvent e) {
		final ProgressbarDialog pd=new ProgressbarDialog(this);
		pd.setTitleMessage("Loading simulations on SCUD file system.");
		pd.setNoteMessage("Please wait.");
		final JProgressBar bar=pd.getProgressBar1();

		pd.setVisible(true);
		bar.setIndeterminate(true);

		class MyTaskConnect extends Thread {

			public void run(){

				updateFileSystem();
				bar.setIndeterminate(false);

				pd.setVisible(false);
			}
		}

		(new MyTaskConnect()).start();


	}

	private void initFileSystem()
	{

		fs_root = new DefaultMutableTreeNode("SCUD FileSystem");
		tree1 = new JTree(fs_root);

	}
	private void doMouseClicked(MouseEvent me)
	{
		TreePath tp = tree1.getPathForLocation(me.getX(), me.getY());
		if(tp !=null)
		{
			DefaultMutableTreeNode node= (DefaultMutableTreeNode) tp.getLastPathComponent();
			if(node!=null && me.getClickCount() == 2)
			{
				if(node.toString().contains("Input"))
				{
					DefaultMutableTreeNode sim_point= (DefaultMutableTreeNode) node.getParent();
					DefaultMutableTreeNode sim_loop= (DefaultMutableTreeNode) node.getParent();


				}
			}

		}

	}
	private void updateFileSystem()
	{
		System.out.println("Loading simulations");
		Simulations sims=controller.getsimulations();
		
		fs_root.removeAllChildren();
		DefaultMutableTreeNode new_sim=null;
		if(sims == null)
		{
			JOptionPane.showMessageDialog(this,"No simulations found on HDFS.");
			return;
		}

		for(Simulation s : sims.getSimulations())
			sims_hdfs.put(s.getId(), s);
		
		Simulation s=null;
		for (String key : sims_hdfs.keySet()) {
			s = sims_hdfs.get(key);
			new_sim = new DefaultMutableTreeNode("Simulation name: "+s.getName());
			fs_root.add(new_sim);

			new_sim.add(new DefaultMutableTreeNode("Author: "+s.getAuthor()));
			new_sim.add(new DefaultMutableTreeNode("Creation time: "+s.getCreationTime()));
			DefaultMutableTreeNode descr=new DefaultMutableTreeNode("Description: "+s.getDescription().substring(0, (s.getDescription().length()>15)?15:s.getDescription().length()-1));
			descr.add(new DefaultMutableTreeNode(s.getDescription()));
			new_sim.add(new DefaultMutableTreeNode("Id: "+s.getId()));
			new_sim.add(new DefaultMutableTreeNode("Toolkit: "+s.getToolkit()));
			new_sim.add(new DefaultMutableTreeNode("Status: "+s.getState()));
			DefaultMutableTreeNode mode=new DefaultMutableTreeNode("Mode: "+(s.getLoop()?"Simulation Optimization":"Parameters Space Exploration"));
			new_sim.add(mode);
			DefaultMutableTreeNode loops=new DefaultMutableTreeNode("Loops");
			new_sim.add(loops);

			if(s.getLoop())
			{
				for(Loop l: s.getRuns().getLoops())
				{
					DefaultMutableTreeNode loop=new DefaultMutableTreeNode("Loop Id: "+l.getId());
					loop.add(new DefaultMutableTreeNode("Status: "+l.getStatus()));	
					loop.add(new DefaultMutableTreeNode("Start time: "+l.getStartTime()));
					loop.add(new DefaultMutableTreeNode("Stop time: "+l.getStopTime()));
					loop.add(new DefaultMutableTreeNode("Loop time: "+l.getLoopTime()));

					class PointTree
					{
						public Input getI() {
							return i;
						}
						public void setI(Input i) {
							this.i = i;
						}
						public Output getO() {
							return o;
						}
						public void setO(Output o) {
							this.o = o;
						}
						private Input i;
						private Output o;
					}
					HashMap<Integer, PointTree> mapio=new HashMap<Integer, PointTree>();

					if(l.getInputs()!=null)
					{
						for(Input i: l.getInputs().getinput_list())
						{
							PointTree p=new PointTree();
							p.setI(i);
							mapio.put(i.id, p);

						}

						if(l.getOutputs()!=null && l.getOutputs().getOutput_list()!=null)
							for(Output i: l.getOutputs().getOutput_list())
							{

								mapio.get(i.getIdInput()).setO(i);
							}
						else System.out.println("No output found.");

						for (Integer pt : mapio.keySet()) {

							DefaultMutableTreeNode point=new DefaultMutableTreeNode("Simulation point: "+pt);
							DefaultMutableTreeNode input=new DefaultMutableTreeNode("Input");
							DefaultMutableTreeNode output=new DefaultMutableTreeNode("Output");


							point.add(input);
							point.add(output);

							for(Parameter p : mapio.get(pt).getI().param_element)
							{
								DefaultMutableTreeNode vname=new DefaultMutableTreeNode("Variable Name: "+p.getvariable_name());
								input.add(vname);
								vname.add(new DefaultMutableTreeNode("Value: "+p.getparam()));
							}
							if(mapio.get(pt).getO()!=null)
								for(Parameter p : mapio.get(pt).getO().output_params)
								{
									DefaultMutableTreeNode vname=new DefaultMutableTreeNode("Variable Name: "+p.getvariable_name());
									output.add(vname);
									vname.add(new DefaultMutableTreeNode("Value: "+p.getparam()));
								}

							loop.add(point);
						}

					}
					loops.add(loop);

				}
			}
		}
		tree1.expandRow(0);

	}
	private DefaultMutableTreeNode fs_root;
	private JMenuBar menuBar1;
	private JMenu menu1;
	private JMenuItem menuItem1;
	private JPanel BackgroundPanel;
	private JPanel ButtonPanel;
	private JButton buttonReload;
	private JButton buttonAdd;
	private JButton buttonSubmit;
	private JButton buttonExport;
	private JButton buttonStop;
	private JPanel MainPanel;
	private JPanel LeftPanel;
	private JScrollPane scrollPane1;
	private JTree tree1;
	private JPanel CenterPanel;
	private JSplitPane splitPane1;
	private JPanel Toppanel;
	private JScrollPane CenterTabbedscrollPane;
	private JTabbedPaneWithCloseIcons CentraltabbedPane;
	private JPanel TestCentralTabbedpanel;
	private JPanel Bottonpanel;
	private JScrollPane CenterBottomscrollPane;
	private JList CenterBottomlist;
	private JFileChooser chooser;
	private HashMap<String, Simulation> sims_hdfs = new HashMap<String, Simulation>();
}
