package com.nwm.assignment.checklistitem;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.nwm.NwmAssignmentApplication;
import com.nwm.assignment.userlist.Checklist;
import com.nwm.assignment.userlist.ChecklistRepository;
import com.nwm.assignment.userlist.User;
import com.nwm.assignment.userlist.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NwmAssignmentApplication.class)
@WebAppConfiguration
public class ChecklistItemControllerTest {

	@Autowired
	private ChecklistItemRepository itemRepository;

	@Autowired
	private ChecklistRepository checklistRepository;

	@Autowired
	private UserRepository userRepository;


	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	private Checklist checklist;
	private User user;
	private ChecklistItem itemFirst,itemSecond,itemThird;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));


	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
				hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();
		user = userRepository.save(new User("aUser"));
		checklist = checklistRepository.save(new Checklist(user,"aChecklist"));
		itemRepository.deleteAll();
		itemFirst = itemRepository.save(new ChecklistItem(checklist,"First Description"));
		itemSecond = itemRepository.save(new ChecklistItem(checklist,"Second Description"));
		itemThird = itemRepository.save(new ChecklistItem(checklist,"Third Description"));
	}

	@Test
	public void getAnItem_Successfully() throws Exception{
		mockMvc.perform( get( "/checklist/" + checklist.getId() + "/item/" + itemSecond.getId()))
		.andExpect( status().isOk())
		.andExpect(jsonPath("$.checklistItem.id",is(itemSecond.getId().intValue())))
		.andExpect(jsonPath("$.checklistItem.description",is(itemSecond.getDescription())))
		.andExpect(jsonPath("$.checklistItem.completed",is(itemSecond.isCompleted())));		
	}

	@Test
	public void getAnItem_Returns404WhenItemDoesNotExist() throws Exception{
		mockMvc.perform( get( "/checklist/" + checklist.getId() + "/item/21212121"))
		.andExpect( status().isNotFound());
	}
	
	@Test
	public void deleteAnItem() throws Exception{
		mockMvc.perform( delete( "/checklist/" + checklist.getId() + "/item/" + itemThird.getId()))
		.andExpect( status().isOk());
	}

	/*
	@Test
	public void createAnItem() throws Exception{
		String itemJson = json(new ChecklistItem(checklist,"A brand new item"));
		this.mockMvc.perform(post("/checklist/" + checklist.getId() + "/item")
				.contentType(contentType)
				.content(itemJson))
		.andExpect(status().isCreated());

	}*/

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
				o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
