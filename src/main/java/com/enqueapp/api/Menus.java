package com.enqueapp.api;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.enqueapp.data.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

/**
 * Defines v1 of a helloworld API, which provides simple "greeting" methods.
 */
@Api(
	name = "menus",
	version = "v1",
	scopes = {Constants.EMAIL_SCOPE},
	clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
	audiences = {Constants.ANDROID_AUDIENCE}
)
public class Menus {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public MenuObject createMenu(@Named("id") String id, @Named("name") String name, @Named("description") String description) {
		Key menuKey = KeyFactory.createKey("Menu", id);

		Entity menu = new Entity("Menu", id);
		menu.setProperty("name", name);
		menu.setProperty("description", description);
		Entity result = nonDuplicatePut("Menu", id, menu);

		return new MenuObject(result.getKey().getName().toString(), 
							result.getProperty("name").toString(), 
							result.getProperty("description").toString());
	}

	public MenuObject getMenu(@Named("id") String id) {
		Key menuKey = KeyFactory.createKey("Menu", id);

		try {
			Entity menu = datastore.get(menuKey);

			return new MenuObject(menu.getKey().getName().toString(), menu.getProperty("name").toString(), menu.getProperty("description").toString());

		} catch (EntityNotFoundException entityNotFound) {

		 	return new MenuObject(id, "undefined", "The Menu you are looking for, id: " + id + ", does not exist");

		}
	}

	public ArrayList<MenuObject> getMenuList() {
		ArrayList<MenuObject> results = new ArrayList<MenuObject>();

		Query q = new Query("Menu");

		List<Entity> menuList = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());

		for (int i = 0; i < menuList.size(); i++) {
			Entity thisMenu = menuList.get(i);
			results.add(new MenuObject(thisMenu.getKey().getName().toString(),
									thisMenu.getProperty("name").toString(), 
									thisMenu.getProperty("description").toString()));
		}

		return results;
	}

	public MenuObject updateMenu(@Named("id") String id, @Named("name") String name, @Named("description") String description) {
		Key menuKey = KeyFactory.createKey("Menu", id);

		try {
			Entity menu = datastore.get(menuKey);
			menu.setProperty("name", name);
			menu.setProperty("description", description);
			datastore.put(menu);

			return new MenuObject(menu.getKey().getName().toString(),
								menu.getProperty("name").toString(), 
								menu.getProperty("name").toString());
		} catch (EntityNotFoundException entityNotFound) {

			return new MenuObject(id, "undefined", "The Menu you tried to update, id: " + id + ", does not exist");

		}
	}

	public MenuObject deleteMenu(@Named("id") String id) {
		Key menuKey = KeyFactory.createKey("Menu", id);

		try {
			Entity result = datastore.get(menuKey);
			datastore.delete(menuKey);
			return new MenuObject(result.getKey().getName().toString(),
								result.getProperty("name").toString(),
								result.getProperty("name").toString());

		} catch (EntityNotFoundException entityNotFound) {

			return new MenuObject(id, "undefined", "The Menu you tried to delete, id: " + id + ", does not exist");

		}
	}


	public MenuItemObject createMenuItem(@Named("menuitemid") String menuItemId, @Named("name") String name, @Named("price") String price, @Named("description") String description, @Named("menuId") String menuId) {
		Key menuKey = KeyFactory.createKey("Menu", menuId);
		String menuItemKeyName = menuId + "__" + menuItemId;

		try {
			Entity menu = datastore.get(menuKey);

			Entity menuItem = new Entity("MenuItem", menuItemKeyName);
			menuItem.setProperty("name", name);
			menuItem.setProperty("price", price);
			menuItem.setProperty("description", description);
			menuItem.setProperty("menuId", menu.getKey().getName());

			Entity result = nonDuplicatePut("MenuItem", menuItemKeyName, menuItem);

			return new MenuItemObject(result.getProperty("name").toString(), 
									result.getProperty("price").toString(),
									result.getProperty("description").toString(),
									result.getProperty("menuId").toString());

		} catch (EntityNotFoundException entityNotFound) {

			return new MenuItemObject("undefined", "0.00", "The Menu you specified, id: " + menuId + ", does not exist", menuId);

		}
	}

	public MenuItemObject getMenuItem(@Named("menuId") String menuId, @Named("menuItemId") String menuItemId) {
		Key menuKey = KeyFactory.createKey("Menu", menuId);
		Key menuItemKey = KeyFactory.createKey("MenuItem", menuId + "__" + menuItemId);

		try {
			Entity menu = datastore.get(menuKey);
			Entity menuItem = datastore.get(menuItemKey);

			// Get from persistent storage menu where menu.id == menuKey
			return new MenuItemObject(menuItem.getProperty("name").toString(),
									menuItem.getProperty("price").toString(),
									menuItem.getProperty("description").toString(),
									menuItem.getProperty("menuId").toString());

		} catch (EntityNotFoundException entityNotFound) {

			return new MenuItemObject("undefined", "0.00", "The Menu Item you specified, menuItemId: " + menuItemId + " from menu: " + menuId + ", does not exist", menuId);

		}
	}

	public MenuItemObject updateMenuItem(@Named("menuitemid") String menuItemId, @Named("name") String name, @Named("price") String price, @Named("description") String description, @Named("menuId") String menuId) {
		Key menuKey = KeyFactory.createKey("Menu", menuId);
		Key menuItemKey = KeyFactory.createKey("MenuItem", menuId + "__" + menuItemId);

		try {
			Entity menu = datastore.get(menuKey);
			Entity menuItem = datastore.get(menuItemKey);

			menuItem.setProperty("name", name);
			menuItem.setProperty("price", price);
			menuItem.setProperty("description", description);
			datastore.put(menuItem);

			return new MenuItemObject(menuItem.getProperty("name").toString(), 
									menuItem.getProperty("price").toString(),
									menuItem.getProperty("description").toString(),
									menuItem.getProperty("menuId").toString());

		} catch (EntityNotFoundException entityNotFound) {

			return new MenuItemObject("undefined", "0.00", "The Menu Item you specified, menuItemId: " + menuItemId + " from menu: " + menuId + ", does not exist", menuId);

		}

	}

	public MenuItemObject deleteMenuItem(@Named("menuId") String menuId, @Named("menuItemId") String menuItemId) {
		Key menuKey = KeyFactory.createKey("Menu", menuId);
		Key menuItemKey = KeyFactory.createKey("MenuItem", menuId + "__" + menuItemId);

		try {
			Entity result = datastore.get(menuItemKey);
			datastore.delete(menuItemKey);

			return new MenuItemObject(result.getProperty("name").toString(), 
									result.getProperty("price").toString(),
									result.getProperty("description").toString(),
									result.getProperty("menuId").toString());

		} catch (EntityNotFoundException entityNotFound) {

			return new MenuItemObject("undefined", "0.00", "The Menu Item you specified, menuItemId: " + menuItemId + " from menu: " + menuId + ", does not exist", menuId);
		}
	}

	public ArrayList<MenuItemObject> getMenuItemsForMenu(@Named("menuId") String menuId) {
		Key menuKey = KeyFactory.createKey("Menu", menuId);
		ArrayList<MenuItemObject> results = new ArrayList<MenuItemObject>();
		Filter propertyFilter = new FilterPredicate("menuId",
													FilterOperator.EQUAL,
													menuKey.getName());

		Query q = new Query("MenuItem").setFilter(propertyFilter);

		List<Entity> menuItemsList = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(5));

		for (int i = 0; i < menuItemsList.size(); i++) {
			Entity thisMenuItem = menuItemsList.get(i);
			results.add(new MenuItemObject(thisMenuItem.getProperty("name").toString(), 
											thisMenuItem.getProperty("price").toString(), 
											thisMenuItem.getProperty("description").toString(),
											thisMenuItem.getProperty("menuId").toString()));
		}

		return results;
	}

	private boolean checkEntityExists(Key entityKey) {
		try {
			datastore.get(entityKey);
			return true;
		} catch (EntityNotFoundException entityNotFound){
			return false;
		}
	}

	private Entity nonDuplicatePut(String entityType, String keyName, Entity entity) {
		Key entityKey = KeyFactory.createKey(entityType, keyName);
		if (!checkEntityExists(entityKey)) {
			datastore.put(entity);
		}

		try {
			return datastore.get(entityKey);
		} catch (EntityNotFoundException entityNotFound){
			return null;
		}

	}

}