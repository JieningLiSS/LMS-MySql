package com.ss.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.ss.dao.AuthorDao;
import com.ss.model.Author;

public class AuthorService {
	private static AuthorService instance = null;

	private AuthorService() {
		// Exists only to defeat instantiation.
	}

	public static AuthorService getInstance() {
		if (instance == null) {
			instance = new AuthorService();
		}
		return instance;
	}

	AuthorDao authorDao = AuthorDao.getInstance();

	public boolean checkAuthor(int authorId) {

		List<Integer> authorList = null;
		try {
			authorList = authorDao.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (authorList.contains(authorId)) {
			return true;
		}
		return false;

	}

	public void addAuthor() {
		// TODO Auto-generated method stub

		System.out.println("Please enter author id:");
		Scanner sc = new Scanner(System.in);
		int authorId = sc.nextInt();

		while (checkAuthor(authorId)) {
			System.out.println("Author id already exist. Enter a new author id:");
			authorId = sc.nextInt();
		}

		System.out.println("Please enter author name:");
		sc = new Scanner(System.in);
		String authorName = sc.nextLine();

		Author author = new Author();
		author.setAuthorId(authorId);
		author.setAuthorName(authorName);

		authorDao.addAuthors(author);

	}

	public void updateAuthor() {
		// TODO Auto-generated method stub

		System.out.println("Please enter author id:");
		Scanner sc = new Scanner(System.in);
		int authorId = sc.nextInt();

		while (!checkAuthor(authorId)) {
			System.out.println("Author id doesn't exist. Enter author id again:");
			authorId = sc.nextInt();
		}
		sc = new Scanner(System.in);
		System.out.println("Please enter new autor name:");
		String newName = sc.nextLine();

		Author author = new Author();
		author.setAuthorId(authorId);
		author.setAuthorName(newName);
		authorDao.updateAuthors(author);
	}

	public void deleteAuthor() {
		// TODO Auto-generated method stub

		System.out.println("Please enter author id:");
		Scanner sc = new Scanner(System.in);
		int authorId = sc.nextInt();

		while (!checkAuthor(authorId)) {
			System.out.println("Author id doesn't exist. Enter author id again:");
			authorId = sc.nextInt();
		}

		Author author = new Author();
		author.setAuthorId(authorId);

		authorDao.deleteAuthor(author);
	}

}
