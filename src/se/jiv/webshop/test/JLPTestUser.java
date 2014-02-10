package se.jiv.webshop.test;

import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.ShoppingCartDAO;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.UserService;

public final class JLPTestUser {

	public static void main(String[] args) {
		UserService userService = new UserService(new UserDAO(),
				new ShoppingCartDAO());

		// addUser
		test1(userService);
		test2(userService);

		// deleteUser
		test3(userService);
		test4(userService);

		// getUser
		test5(userService);
		test6(userService);

		// updateUser
		test7(userService);
		test8(userService);

		// addProductToCart
		test9(userService);
		test10(userService);
		test11(userService);

		// getShoppingCartContents
		test12(userService);
		test13(userService);

		// updateCart
		test18(userService);
		test19(userService);
		test20(userService);
		test21(userService);

	}

	private static void test21(UserService userService) {
		try {
			UserModel user = userService.getUser("apple@apple.com");
			userService.updateCart(user, 1, -1);
			Map<Integer, Integer> sc = userService
					.getShoppingCartContents(user);
			System.out.println("TEST 21 -->RESULT: " + sc.size()
					+ " OK if Result=0, NOK other value");
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 21");
			return;
		}
	}

	private static void test20(UserService userService) {
		try {
			UserModel user = userService.getUser("apple@apple.com");
			userService.updateCart(user, -1, 2);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 20");
			return;
		}
		System.out.println("OK TEST 20");
	}

	private static void test19(UserService userService) {
		try {
			userService.updateCart(null, -1, -2);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 19");
			return;
		}
		System.out.println("OK TEST 19");
	}

	private static void test18(UserService userService) {
		try {
			UserModel user = userService.getUser("apple@apple.com");
			userService.addProductToCart(user, 1, 1);
			userService.addProductToCart(user, 1, 3);

			Map<Integer, Integer> sc = userService
					.getShoppingCartContents(user);

			if (sc.get(1) == 4) {
				System.out.println("OK TEST 18");
			} else {
				System.out.println("NOK TEST 18");
			}

			userService.removeProductFromCart(user, 1);
		} catch (WebshopAppException e) {
			e.printStackTrace();
			System.out.println("exception");
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("FAILED TEST 18");
			return;
		}
	}

	private static void test13(UserService userService) {
		try {
			UserModel user = new UserModel.Builder(null, null, null, null,
					null, null, null).build();
			userService.getShoppingCartContents(user);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 13");
			return;
		}
		System.out.println("OK TEST 13");

	}

	private static void test12(UserService userService) {
		try {
			userService.getShoppingCartContents(null);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 12");
			return;
		}
		System.out.println("OK TEST 12");

	}

	private static void test11(UserService userService) {
		try {
			UserModel user = userService.getUser("apple@apple.com");
			userService.addProductToCart(user, -1, 2);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 11");
			return;
		}
		System.out.println("OK TEST 11");

	}

	private static void test10(UserService userService) {
		try {
			UserModel user = new UserModel.Builder(null, null, null, null,
					null, null, null).build();
			userService.addProductToCart(user, -1, 2);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 10");
			return;
		}
		System.out.println("OK TEST 10");

	}

	private static void test9(UserService userService) {
		try {
			userService.addProductToCart(null, -1, 2);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 9");
			return;
		}
		System.out.println("OK TEST 9");
	}

	private static void test8(UserService userService) {
		try {
			UserModel user = new UserModel.Builder(null, null, null, null,
					null, null, null).build();
			userService.updateUser(user);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 8");
			return;
		}
		System.out.println("OK TEST 8");
	}

	private static void test7(UserService userService) {
		try {
			userService.updateUser(null);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 7");
			return;
		}
		System.out.println("OK TEST 7");
	}

	private static void test6(UserService userService) {
		try {

			UserModel user = userService.getUser("asdfadsfasdfa");
			System.out.println(user);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 6");
			return;
		}
		System.out.println("OK TEST 6");
	}

	private static void test5(UserService userService) {
		try {

			UserModel user = userService.getUser(null);
			System.out.println(user);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 5");
			return;
		}
		System.out.println("OK TEST 5");
	}

	private static void test4(UserService userService) {
		try {
			UserModel user = new UserModel.Builder(null, null, null, null,
					null, null, null).build();
			userService.deleteUser(user);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 4");
			return;
		}
		System.out.println("OK TEST 4");
	}

	private static void test3(UserService userService) {
		try {
			userService.deleteUser(null);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 3");
			return;
		}
		System.out.println("OK TEST 3");

	}

	private static void test2(UserService userService) {
		try {
			UserModel user = new UserModel.Builder(null, null, null, null,
					null, null, null).build();
			userService.addUser(user);
		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 2");
			return;
		}
		System.out.println("OK TEST 2");

	}

	private static void test1(UserService userService) {
		try {

			userService.addUser(null);

		} catch (WebshopAppException e) {
		} catch (Exception e1) {
			System.out.println("FAILED TEST 1");
			return;
		}
		System.out.println("OK TEST 1");

	}

}
