package com.studentdating.studentdating.controller;

import com.studentdating.studentdating.model.dto.MessageDTO;
import com.studentdating.studentdating.model.dto.UserDTO;
import com.studentdating.studentdating.model.exception.DomainException;
import com.studentdating.studentdating.model.exception.NotAuthorizedException;
import com.studentdating.studentdating.model.service.MessageService;
import com.studentdating.studentdating.model.service.RelationService;
import com.studentdating.studentdating.model.service.UserService;
import com.studentdating.studentdating.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
//import javax.validation.Valid;

@Controller
public class UserController {
	@Autowired
	private final UserService userService;
	@Autowired
	private final RelationService relationService;
	@Autowired
	private final MessageService chatService;

// TODO ALLE LOGICA IN SERVICE? ENKEL VIEWS CONTROLLEN IN CONTROLLER
	public UserController(UserService userService, RelationService relationService, MessageService chatService) {
		this.userService = userService;
		this.relationService = relationService;
		this.chatService = chatService;
	}

	// TODO elke methode (get of post) moet eerst setUserInSession(session, model) oproepen tegen errors

	@GetMapping("/testtwo")
	public String getTwo() { // todo wegdoen
		return "todo";
	}

	@GetMapping("/signup")
	public String getCreateUser(Model model) {
		model.addAttribute("newUser", new UserDTO());
		return "signup";
	}

	@GetMapping("/test") //todo wegdoen
	public String getTest() {
		return "test";
	}

	// @RequestBody String request || @RequestParam("firstName") String firstName || @ModelAttribute("newUser") @Valid UserDTO user
	@PostMapping("/signup")
	public String postCreateUser(@Validated @ModelAttribute("newUser") UserDTO user, BindingResult bindingResult, Model model) {
		ArrayList<String> errors = new ArrayList<>();
		if (user.getBirthDate() != null) {
			if (Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 14 || Period.between(user.getBirthDate(), LocalDate.now()).getYears() > 40)
				errors.add("You are too young");
			}
			else errors.add("Birthdate is empty");
		if (user.getSex() != null) {
			if (!user.getSex().equals("M") && !user.getSex().equals("F"))
				errors.add("Sex is invalid");
		}
		// unique email
		UserDTO u = null;
		try {
			if (user.getUsername() != null) u = userService.getUser(user.getUsername());
		} catch (Exception x) {
			errors.add("GetUser: " + x.getMessage()); // todo remove GetUser: //todo geeft error
		}
		if (u != null) errors.add("Email is already taken"); //todo db exception?
		if (bindingResult.hasErrors() || errors.size() != 0) {//todo check bindingresult
			for (FieldError fe: bindingResult.getFieldErrors()) {
				errors.add(fe.getDefaultMessage().split(":")[1]);
			}
			model.addAttribute("errors", errors);
			return "signup"; //todo gaat errors kwijt?
		}
		// DomainException from UserService when passwords do not match
		try {
			userService.createUser(user);
		} catch (Exception x) { //todo Domain?
			errors.add(x.getMessage());
			model.addAttribute("errors", errors);
			return "signup";
		}
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String getLoginForm() {
		return "login";
	}

	@PostMapping("/login")
	public String loginSessionUser() {
		System.out.println("================Loggin");//todo
		return "index";
	}

	private void setUserInSession(HttpSession session, Model model) {
		UserDTO user = userService.getLoggedIn();
		if (user != null) {
			session.setAttribute("user", user);
			model.addAttribute("loggedinUser", user);//todo why?
		}
	}
/*
	@GetMapping("/logout")
	public String logout() {
		System.out.println("logout");//Todo
		return "redirect:/";
	}*/

	@GetMapping("/")
	public String getHome(HttpSession session, Model model) {
		setUserInSession(session, model);
		model.addAttribute("persons", userService.getUsers());
		session.setAttribute("user", userService.getLoggedIn()); //todo session or model?
		//model.addAttribute("user", userService.getLoggedIn());
		return "index";
	}

	@GetMapping("/profile")
	public String myProfile(HttpSession session, Model model) {
		setUserInSession(session, model);
		UserDTO user = userService.getLoggedIn();
		model.addAttribute("user", user);
		model.addAttribute("relations", relationService.getRelations(user.getFirstName() + " " + user.getLastName()));
		return "profile";
	}

	@GetMapping("/showProfile&id=id")
	public String showProfile(@RequestParam(name = "id") long id, Model model, HttpSession session) {
		setUserInSession(session, model);
		UserDTO user = userService.getUser(id);
		if (user != null) {
			model.addAttribute("user", user);
			return "otherProfile";
		}
		else return "redirect:/everyone";
	}

	@GetMapping("/everyone")
	public String getEveryone(HttpSession session, Model model) {
		setUserInSession(session, model);
		model.addAttribute("persons", userService.getFriends());
		return "everyone";
	}

	@GetMapping("/relations")
	public String getRelations(HttpSession session, Model model) {
		setUserInSession(session, model);
		/*if (!service.getLoggedIn().getRole().equals(Role.ADMIN)) {
			//todo put error
			return "index";
		}*/ //todo already defined in securityconfig
		System.out.println("Get relations"); //todo
		model.addAttribute("relations", relationService.getRelations());
		return "relations";
	}

	@PostMapping("/relation")
	public String makeRelation(@RequestBody String request, HttpSession session, Model model) {
		setUserInSession(session, model);
		String personA = request.split("&")[0].substring(8).replace("+", " ");
		String personB = request.split("&")[1].substring(8).replace("+", " ");
		UserDTO user = (UserDTO) session.getAttribute("user");
		String choser = user.getFirstName() + " " + user.getLastName();
		
		relationService.makeRelation(choser, personA, personB);
		return "redirect:/";
	}

	@GetMapping("/chat")
	public String getChatPage(HttpSession session, Model model) {
		setUserInSession(session, model);
		model.addAttribute("persons", userService.getFriends());
		return "chat";
	}

	//PAGE ONLY FOR ADMINS, pure for testing the AuthZ & AuthN
	@GetMapping("/admin")
	public String getAdminpanel(HttpSession session, Model model) {
		setUserInSession(session, model);
		if (userService.getLoggedIn() != null) {
			try {
				//Role[] roles = {Role.ADMIN};
				Utility.checkRole(session, "administrator");
				UserDTO user = (UserDTO) session.getAttribute("user");
				model.addAttribute("loggedinUser", user);
				model.addAttribute("rolUser", user.getRole());
				model.addAttribute("users", userService.getUsers());
				return "admin";
			} catch (NotAuthorizedException ex) {
				System.out.println("Admin controller not autho: " + ex.getMessage()); //todo
				model.addAttribute("notAuthorized","You have insufficient rights to view this page");
				return "admin";
			}
		}
		model.addAttribute("rolUser",null);
		model.addAttribute("loggedinUser", null);
		model.addAttribute("users", null);
		return "redirect:/";
	}

	// POST EN GET REQUESTS

	@ResponseBody
	@GetMapping("/chats")
	public List<MessageDTO> getAllChats(HttpSession session, Model model) {
		setUserInSession(session, model);
		System.out.println("=================== amount of chats: " + (chatService.getMessages(userService.getLoggedIn().getUsername())).size());//todo
		return chatService.getMessages(userService.getLoggedIn().getUsername()); // todo use username for messages
	}

	@PostMapping("/createChat")
	public String createChat(@ModelAttribute MessageDTO messageDTO, HttpSession session, Model model) {
		setUserInSession(session, model);
		chatService.setMessage(messageDTO);
		System.out.println("Creating message: " + messageDTO.getOntvanger()); //todo
		return "redirect:/";
	}

	@PostMapping("/sendMessageToChat")
	public String sendMessageToChat(@RequestBody String request, @ModelAttribute MessageDTO message, HttpSession session, Model model) {
		setUserInSession(session, model);
		if (session.getAttribute("user") != null) {
			System.out.println(message.toString());//todo
			System.out.println("req: " + request);//todo
			chatService.setMessage(message);
		}
		return "redirect:/chatPage";
	}

	@GetMapping("/edit")
	public String getEdit(HttpSession session, Model model) {
		setUserInSession(session, model);
		model.addAttribute("user", new UserDTO());
		return "edit";
	}

	@PostMapping("/edit")
	public String edit(@ModelAttribute("user") UserDTO user, HttpSession session, Model model) {
		setUserInSession(session, model);
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String location = user.getLocation();
		UserDTO actual = userService.getLoggedIn();
		if (!firstName.trim().isEmpty()) actual.setFirstName(firstName);
		if (!lastName.trim().isEmpty()) actual.setLastName(lastName);
		if (!location.trim().isEmpty()) actual.setLocation(location);
		userService.update(actual);
		return "redirect:/profile";
	}

/*	@ResponseBody
	@GetMapping("/getFriends")
	public List<UserDTO> getFriendsList(HttpSession session) {
		List<UserDTO> a = new ArrayList<>();
		if (session.getAttribute("user") != null) {
			UserDTO person = (UserDTO) session.getAttribute("user");
			a = userService.getUser(person.getId()).getFriends();
		}
		return a;
	}*/


/*


	@ResponseBody
	@GetMapping("/getPersonalChats")
	public List<Chat> getPersonalChats(HttpSession session) {
		List<Chat> a = new ArrayList<>();
		if (session.getAttribute("user") != null) {
			Person person1 = (Person) session.getAttribute("user");
			a = chatService.getPersonalChats(person1.getFirstName());
			return a;
		}
		return a;
	}


	@ResponseBody
	@GetMapping("/getAllMessagesFromChat")
	public List<String> getAllMessagesFromChat(HttpSession session) {
		List<String> a = new ArrayList<>();
		if (session.getAttribute("user") != null) {
			a = chatService.getAllMessagesFromChat();
			return a;
		}
		return a;
	}
*/

/*	@PostMapping("/registerNewPerson")
	public String registerNewPerson(@ModelAttribute UserDTO person) {
		if (person.getPassword().equalsIgnoreCase(person.getReppassword())) {
			person.setRole(Role.LID); //todo geen field rol bij signup
			service.addPerson(person);
		}
		else {
			//todo throw new DomainException("Passwords do not match");
			return "login";
		}
		return "redirect:/";
	}*/

	@PostMapping("/updatePerson")
	public void updatePerson(@ModelAttribute UserDTO person, HttpSession session, Model model) {
		setUserInSession(session, model);
		System.out.println("UPDATING STARTING");
		System.out.println(person);
		UserDTO retrievedPerson = userService.getUser(person.getId());
		retrievedPerson.setId(person.getId());
		retrievedPerson.setFirstName(person.getFirstName());
		System.out.println("editted person!");
	}
}
