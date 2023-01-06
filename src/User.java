
/* 用户User类 */ 

class User implements Comparable<User>{
	private String account;	
	private String pwd;
	private int bestscore;

	// 构造函数
	public User(String account, String pwd) {
		this.account = account;
		this.pwd = pwd;
		this.bestscore = 0;
	}

	// 账户
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	// 密码
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	// 分数
	public void setBestScore(int bestscore) {
		this.bestscore = bestscore;
	}

	public int getBestScore() {
		return bestscore;
	}
	
	// 重写equals方法,为了实现账户和密码相同的User是同一个对象。
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			if (this.getAccount().equals(((User) obj).getAccount())
					&& this.getPwd().equals(((User) obj).getPwd())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	// 重写compareTo方法,为了实现根据分数排序。
	@Override
    public int compareTo(User u) {
        return u.getBestScore() - this.getBestScore();
    }
}

