package com.ss.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.ss.dao.AuthorDao;
import com.ss.dao.PublisherDao;
import com.ss.model.Publisher;

public class PublisherService {

	private static PublisherService instance = null;

	private PublisherService() {
		// Exists only to defeat instantiation.
	}

	public static PublisherService getInstance() {
		if (instance == null) {
			instance = new PublisherService();
		}
		return instance;
	}

	PublisherDao publisherDao = PublisherDao.getInstance();

	public boolean checkPublisher(int bookId) {

		List<Integer> publisherList = null;
		try {
			publisherList = publisherDao.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (publisherList.contains(bookId)) {
			return true;
		}
		return false;

	}

	public void addPublisher() {
		// TODO Auto-generated method stub

		System.out.println("Please enter publisher id:");
		Scanner sc = new Scanner(System.in);
		int publisherId = sc.nextInt();

		while (checkPublisher(publisherId)) {
			System.out.println("Publisher id already exist. Enter a new publisher id:");
			publisherId = sc.nextInt();
		}

		System.out.println("Please enter publisher name:");
		sc = new Scanner(System.in);
		String publisherName = sc.nextLine();

		System.out.println("Please enter publisher address:");
		sc = new Scanner(System.in);
		String publisherAddress = sc.nextLine();

		System.out.println("Please enter publisher phone number:");
		sc = new Scanner(System.in);
		String publisherPhone = sc.nextLine();

		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhone(publisherPhone);

		publisherDao.addPublishers(publisher);

	}

	public void updatePublisher() {
		// TODO Auto-generated method stub

		System.out.println("Please enter publisher id:");
		Scanner sc = new Scanner(System.in);
		int publisherId = sc.nextInt();

		while (!checkPublisher(publisherId)) {
			System.out.println("Publisher doesn't exist. Enter publisher id again:");
			publisherId = sc.nextInt();
		}

		System.out.println("Please enter new publisher name:");
		sc = new Scanner(System.in);
		String newPublisherName = sc.nextLine();

		System.out.println("Please enter new publisher address:");
		sc = new Scanner(System.in);
		String newPublisherAddress = sc.nextLine();

		System.out.println("Please enter new publisher phone number:");
		sc = new Scanner(System.in);
		String newPublisherPhone = sc.nextLine();

		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);
		publisher.setPublisherName(newPublisherName);
		publisher.setPublisherAddress(newPublisherAddress);
		publisher.setPublisherPhone(newPublisherPhone);

		publisherDao.updatePublishers(publisher);

	}

	public void deletePublisher() {
		// TODO Auto-generated method stub

		System.out.println("Please enter publisher id:");
		Scanner sc = new Scanner(System.in);
		int publisherId = sc.nextInt();

		while (!checkPublisher(publisherId)) {
			System.out.println("Publisher doesn't exist. Enter publisher id again:");
			publisherId = sc.nextInt();
		}

		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);

		publisherDao.deletePublishers(publisher);

	}

}
