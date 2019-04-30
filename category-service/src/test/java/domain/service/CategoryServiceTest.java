package domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import eu.drus.jpa.unit.api.JpaUnit;

import domain.model.Category;

@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@Spy
	@PersistenceContext(unitName = "CategoryPUTest")
	EntityManager em;
	
	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;

//	@Test
//	public void testGetAll() {
//		int size = initDataStore();
//		assertEquals(size, categoryServiceImpl.getAll().size());
//}
//	
	@Test
	public void testCreate() {
		Category category = getRandomCategory();
		categoryServiceImpl.create(category);
		
		Category i = em.find(Category.class, category.getId());
		
		assertTrue(category.equals(i));
	}
	
	@Test
	public void testDelete() {
		
		Category category = getRandomCategory();
		categoryServiceImpl.create(category);
		categoryServiceImpl.delete(category);
		
		Category i = em.find(Category.class, category.getId());
		
		assertTrue(i == null);
}
	
	
	
	private List<Category> getCategories() {

		List<Category> categories = new ArrayList<>();
		long numberOfCategory = Math.round((Math.random() * 10)) + 1;
		for (int i = 0; i < numberOfCategory; i++) {
			categories.add(getRandomCategory());
		}
		return categories;
	}
	
	private int initDataStore() {
		int size = categoryServiceImpl.getAll().size();
		List<Category> categories = getCategories();	
		for (Category category : categories) {
			categoryServiceImpl.create(category);
		}
		return size + categories.size();
}
	
	private Category getRandomCategory() {
		Category category = new Category();
		category.setName(UUID.randomUUID().toString());

		return category;
}

}
