package com.ss.daoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ss.dao.AuthorDao;
import com.ss.db.DBConnection;
import com.ss.model.Author;

@RunWith(MockitoJUnitRunner.class)
class AuthorDaoTest {
	@Mock
	private DBConnection ds;
	@Mock
	private Connection c;
	@Mock
	private PreparedStatement stmt;
	@Mock
	private ResultSet rs;
	
	private Author author;

	@Before
	public void setUp() throws Exception {
		assertNotNull(ds);
		when(c.prepareStatement(any(String.class))).thenReturn(stmt);
		when(ds.getConnection()).thenReturn(c);
		author = new Author();
		author.setAuthorId(10);
		author.setAuthorName("Johannes");
		when(rs.first()).thenReturn(true);
		when(rs.getInt(1)).thenReturn(1);
		when(rs.getString(2)).thenReturn(author.getAuthorName());
		when(stmt.executeQuery()).thenReturn(rs);
	}
	
	@Test
	    public void createAuthor() {
	        new AuthorDao(ds).addAuthors(author);
	        assertEquals("Johannes", author.getAuthorName());
	    }
	
	 @Test
	    public void createAndRetrievePerson() throws Exception {
		 AuthorDao dao = new AuthorDao(ds);
	        dao.addAuthors(author);
	        assertSame("James", author.getAuthorName());
	    }
	  

	

}