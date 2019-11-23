package com.ss.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import com.ss.dao.AuthorDao;
import com.ss.dao.BookDao;
import com.ss.dao.PublisherDao;
import com.ss.model.Author;
import com.ss.model.Book;
import com.ss.model.Publisher;

public class BookService {
	private static BookService instance = null;

	private BookService() {
		// Exists only to defeat instantiation.
	}

	public static BookService getInstance() {
		if (instance == null) {
			instance = new BookService();
		}
		return instance;
	}

	BookDao bookDao = BookDao.getInstance();

	public boolean checkBook(int bookId) {

		List<Integer> bookList = null;
		try {
			bookList = bookDao.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bookList.contains(bookId)) {
			return true;
		}
		return false;
	}

	public void addBook() {
		// TODO Auto-generated method stub
		System.out.println("Please enter book id:");
		Scanner sc = new Scanner(System.in);
		int bookId = sc.nextInt();

		while (checkBook(bookId)) {
			System.out.println("Book id already exist! Enter a new book id again:");
			bookId = sc.nextInt();
		}

		System.out.println("Please enter book name:");
		sc = new Scanner(System.in);
		String bookName = sc.nextLine();

		System.out.println("Please enter author id:");
		sc = new Scanner(System.in);
		int authorId = sc.nextInt();

		AuthorService authorService = AuthorService.getInstance();
		while (!authorService.checkAuthor(authorId)) {
			System.out.println("Author id doesn't exist!Enter author id again:");
			authorId = sc.nextInt();
		}

		System.out.println("Please enter publisher id:");
		sc = new Scanner(System.in);
		int publisherId = sc.nextInt();

		PublisherService publisherService = PublisherService.getInstance();
		while (!publisherService.checkPublisher(publisherId)) {
			System.out.println("Publisher id doesn't exist!Enter publisher id again:");
			publisherId = sc.nextInt();
		}

		Book book = new Book();
		book.setBookId(bookId);
		book.setTitle(bookName);

		Author author = new Author();
		author.setAuthorId(authorId);

		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);

		book.setAuthor(author);
		book.setPublisher(publisher);
		bookDao.addBooks(book);

	}

	public void updateBook() {
		// TODO Auto-generated method stub
		System.out.println("Please enter book id:");
		Scanner sc = new Scanner(System.in);
		int bookId = sc.nextInt();

		while (!checkBook(bookId)) {
			System.out.println("Book id doesn't exist! Enter book id again:");
			bookId = sc.nextInt();
		}

		System.out.println("Please enter book name:");
		sc = new Scanner(System.in);
		String updateBookName = sc.nextLine();

		System.out.println("Please enter author id:");
		sc = new Scanner(System.in);
		int authorId = sc.nextInt();

		AuthorService authorService = AuthorService.getInstance();
		while (!authorService.checkAuthor(authorId)) {
			System.out.println("Author id doesn't exist!Enter author id again:");
			authorId = sc.nextInt();
		}

		System.out.println("Please enter publisher id:");
		sc = new Scanner(System.in);
		int publisherId = sc.nextInt();

		PublisherService publisherService = PublisherService.getInstance();
		while (!publisherService.checkPublisher(publisherId)) {
			System.out.println("Publisher id doesn't exist!Enter publisher id again:");
			publisherId = sc.nextInt();
		}

		Book book = new Book();
		book.setBookId(bookId);
		book.setTitle(updateBookName);

		Author updateAuthor = new Author();
		updateAuthor.setAuthorId(authorId);

		Publisher updatePublisher = new Publisher();
		updatePublisher.setPublisherId(publisherId);

		book.setAuthor(updateAuthor);
		book.setPublisher(updatePublisher);

		bookDao.updateBooks(book);
	}

	public void deleteBook() {
		// TODO Auto-generated method stub

		System.out.println("Please enter book id:");
		Scanner sc = new Scanner(System.in);
		int bookId = sc.nextInt();

		while (!checkBook(bookId)) {
			System.out.println("Book id doesn't exist! Enter book id again:");
			bookId = sc.nextInt();
		}

		Book book = new Book();
		book.setBookId(bookId);
		bookDao.deleteBook(book);

	}

}
