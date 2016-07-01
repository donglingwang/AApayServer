package com.bishe.aapay.service;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bishe.aapay.dao.CategoryDao;
import com.bishe.aapay.dao.ConsumptionDao;
import com.bishe.aapay.dao.ParticipantDao;
import com.bishe.aapay.dao.PaymentDao;
import com.bishe.aapay.dao.PersonalBudgetDao;
import com.bishe.aapay.dao.TeamDao;
import com.bishe.aapay.dao.UserDao;
import com.bishe.aapay.dto.Category;
import com.bishe.aapay.dto.Consumption;
import com.bishe.aapay.dto.Participant;
import com.bishe.aapay.dto.Payment;
import com.bishe.aapay.dto.PersonalBudget;
import com.bishe.aapay.dto.Team;
import com.bishe.aapay.dto.User;

public class Server {

	private final static Logger logger = Logger.getLogger(Server.class.getName());
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(30002);
		while (true) {
			Socket socket = server.accept();
			invoke(socket);
		}
	}

	private static void invoke(final Socket socket) throws IOException {
		new Thread(new Runnable() {
			public void run() {
				ObjectInputStream ois = null;
				InputStream is = null;
				ObjectOutputStream oos = null;
				OutputStream os = null;
				BufferedReader br = null;
				try {
					is = socket.getInputStream();
					br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
					String flag = br.readLine();
					System.out.println(flag);
					if(flag.equals("readUser")) {
						String email = br.readLine();
						User user = UserDao.getInstance().find(email);
						oos = new ObjectOutputStream(socket.getOutputStream());
						oos.writeObject(user);
						oos.flush();
					}
					else if(flag.equals("sendUser")) {
						ois = new ObjectInputStream(new BufferedInputStream(is));
						os = socket.getOutputStream();
						Object obj = ois.readObject();
						User user = (User)obj;
						if(UserDao.getInstance().insert(user)) {
							os.write("success\n".getBytes("UTF-8"));
						} 
						else {
							os.write("failure\n".getBytes("UTF-8"));
						}
						os.flush();
						System.out.println("user: " + user.getUserName() + "/" + user.getPassword());
					}
					//备份Category表
					else if(flag.equals("backupCategory")) {
						ois = new ObjectInputStream(new BufferedInputStream(is));
						os = socket.getOutputStream();
						int userId = ois.readInt();
						Object category = ois.readObject();
						List<Category> categories = (List<Category>)category;
						CategoryDao.getInstance().deleteAll(userId);
						if(CategoryDao.getInstance().batchInsert(categories,userId)) {
							os.write("success\n".getBytes("UTF-8"));
						} 
						else {
							os.write("failure\n".getBytes("UTF-8"));
						}
						os.flush();
					}
					//备份Consumption表
					else if(flag.equals("backupConsumption")) {
						ois = new ObjectInputStream(new BufferedInputStream(is));
						os = socket.getOutputStream();
						int userId = ois.readInt();
						//
						Object consumption = ois.readObject();
						List<Consumption> consumptions = (List<Consumption>) consumption;
						ConsumptionDao.getInstance().deleteAll(userId);
						if(ConsumptionDao.getInstance().insert(consumptions, userId)) {
							os.write("success\n".getBytes("UTF-8"));
						} 
						else {
							os.write("failure\n".getBytes("UTF-8"));
						}
						os.flush();
					}
					//备份Participant表
					else if(flag.equals("backupParticipant")) {
						ois = new ObjectInputStream(new BufferedInputStream(is));
						os = socket.getOutputStream();
						int userId = ois.readInt();
						Object participant = ois.readObject();
						List<Participant> participants = (List<Participant>) participant;
						ParticipantDao.getInstance().deleteAll(userId);
						if(ParticipantDao.getInstance().batchInsert(participants, userId)) {
							os.write("success\n".getBytes("UTF-8"));
						} 
						else {
							os.write("failure\n".getBytes("UTF-8"));
						}
						os.flush();
					}
					//备份payment表
					else if(flag.equals("backupPayment")) {
						ois = new ObjectInputStream(new BufferedInputStream(is));
						os = socket.getOutputStream();
						int userId = ois.readInt();
						Object payment = ois.readObject();
						List<Payment> payments = (List<Payment>) payment;
						PaymentDao.getInstance().deleteAll(userId);
						if(PaymentDao.getInstance().batchInsert(payments, userId)) {
							os.write("success\n".getBytes("UTF-8"));
						} 
						else {
							os.write("failure\n".getBytes("UTF-8"));
						}
						os.flush();
					}
					//备份budget表
					else if(flag.equals("backupBudget")) {
						ois = new ObjectInputStream(new BufferedInputStream(is));
						os = socket.getOutputStream();
						int userId = ois.readInt();
						Object budget = ois.readObject();
						List<PersonalBudget> budgets = (List<PersonalBudget>) budget;
						PersonalBudgetDao.getInstance().deleteAll(userId);
						if(PersonalBudgetDao.getInstance().batchInsert(budgets, userId)) {
							os.write("success\n".getBytes("UTF-8"));
						} 
						else {
							os.write("failure\n".getBytes("UTF-8"));
						}
						os.flush();
					}
					//备份team表
					else if(flag.equals("backupTeam")) {
						ois = new ObjectInputStream(new BufferedInputStream(is));
						os = socket.getOutputStream();
						int userId = ois.readInt();	
						System.out.println(userId);
						Object team = ois.readObject();
						List<Team> teams = (List<Team>) team;
						TeamDao.getInstance().deleteAll(userId);
						if(TeamDao.getInstance().batchInsert(teams, userId)) {
							os.write("success\n".getBytes("UTF-8"));
						} 
						else {
							os.write("failure\n".getBytes("UTF-8"));
						}
						os.flush();
					}
					else if(flag.equals("dataRecovery")) {
						ois = new ObjectInputStream(new BufferedInputStream(is));
						int userId = ois.readInt();
						System.out.println(userId);
						oos = new ObjectOutputStream(socket.getOutputStream());
						List<Category> categories = CategoryDao.getInstance().findAll(userId);
						List<Consumption> consumptions = ConsumptionDao.getInstance().findAll(userId);
						List<Participant> participants = ParticipantDao.getInstance().findAll(userId);
						List<PersonalBudget> personalBudgets = PersonalBudgetDao.getInstance().findAll(userId);
						List<Payment> payments = PaymentDao.getInstance().findAll(userId);
						List<Team> teams = TeamDao.getInstance().findAll(userId);
						
						System.out.println(categories.size());
						oos.writeObject(categories);
						oos.flush();
						System.out.println(consumptions.size());
						oos.writeObject(consumptions);
						oos.flush();
						
						oos.writeObject(participants);
						oos.flush();
						System.out.println(participants.size());
						
						oos.writeObject(payments);
						oos.flush();
						System.out.println(payments.size());
						
						oos.writeObject(personalBudgets);
						oos.flush();
						System.out.println(personalBudgets);
						System.out.println(teams.size());
						oos.writeObject(teams);
						oos.flush();
					}
				} catch (IOException ex) {
					logger.log(Level.SEVERE, null, ex);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						if(ois != null) {
							ois.close();
						}
						if (is != null) {
							is.close();
						}
						if(oos != null) {
							oos.close();
						}
						if(os != null) {
							os.close();
						}
						if(socket != null) {
							socket.close();
						}
					} catch(Exception ex) {}
				}
			}
		}).start();
	}
}