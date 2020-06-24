package dev.throwouterror.util;

import java.io.Serializable;

import com.google.gson.JsonObject;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public interface ISerializable extends Serializable {
	String toString();

	ISerializable fromString(String s);
}