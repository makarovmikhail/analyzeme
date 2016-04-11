package com.analyzeme.data;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lagroffe on 30.03.2016 13:17
 */

public class DataSet {
	private String nameForUser;
	private Set<String> fields;
	private ISourceInfo file;

	public DataSet(final String nameForUser, final ISourceInfo file) throws Exception {
		if (nameForUser == null || nameForUser.equals("") || file == null)
			throw new IllegalArgumentException();
		this.nameForUser = nameForUser;
		this.file = file;
		fields = new HashSet<String>();
	}

	public ByteArrayInputStream getData() throws Exception {
		return file.getFileData();
	}

	public String getNameForUser() {
		return nameForUser;
	}

	public void addField(final String field) throws Exception {
		if (field == null || field.equals(""))
			throw new IllegalArgumentException();
		fields.add(field);
	}

	public Set<String> getFields() {
		return fields;
	}

	public ISourceInfo getFile() {
		return file;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof DataSet))return false;
		try {
			DataSet o = (DataSet) other;
			if ((o.getNameForUser().equals(nameForUser)) && (o.getFile().getClass().equals(file.getClass())) && (o.getFile().getToken().equals(file.getToken())))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}