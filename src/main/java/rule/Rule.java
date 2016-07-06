package rule;

/**
 * 规则类
 * 
 * @author lzg
 * @date 2016年7月6日
 */
public class Rule {
	/**
	 * 链接
	 */
	private String url;

	/**
	 * 参数集合
	 */
	private String[] params;
	/**
	 * 参数对应的值
	 */
	private String[] values;

	/**
	 * 过滤的属性名 和下面的type联合使用,如 得到 class=filterAttrName 的所有元素
	 */
	private String filterAttrName;

	/**
	 * CLASS / ID / SELECTION 按class,id,select 过滤结果
	 */
	private int type = ID;

	/**
	 * GET / POST 请求的类型，默认GET
	 */
	private int requestMoethod = GET;

	public final static int GET = 0;
	public final static int POST = 1;

	public final static int CLASS = 0;
	public final static int ID = 1;
	public final static int SELECTION = 2;

	public Rule() {
	}

	public Rule(String url, String filterAttrName, int type, int requestMoethod) {
		super();
		this.url = url;
		this.filterAttrName = filterAttrName;
		this.type = type;
		this.requestMoethod = requestMoethod;
	}

	public Rule(String url, String[] params, String[] values, String filterAttrName, int type, int requestMoethod) {
		super();
		this.url = url;
		this.params = params;
		this.values = values;
		this.filterAttrName = filterAttrName;
		this.type = type;
		this.requestMoethod = requestMoethod;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String getFilterAttrName() {
		return filterAttrName;
	}

	public void setFilterAttrName(String filterAttrName) {
		this.filterAttrName = filterAttrName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRequestMoethod() {
		return requestMoethod;
	}

	public void setRequestMoethod(int requestMoethod) {
		this.requestMoethod = requestMoethod;
	}

}
