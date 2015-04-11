package com.example.drugsformarinemammals;

public class Article_Reference {
	
	private char index;
	private String article;
	
	public Article_Reference(char index, String article) {
		this.index = index;
		this.article = article;
	}
	
	public char getIndex() {
		return index;
	}
	
	public void setIndex(char article_index) {
		index = article_index;
	}
	
	public String getArticle() {
		return article;
	}
	
	public void setArticle(String reference) {
		article = reference;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof Article_Reference))
			return false;
		Article_Reference article_reference = (Article_Reference) o;
		if (index != article_reference.getIndex())		
			return false;
		if (!article.equals(article_reference.getArticle()))		
			return false;
		return true;
	 }

}
