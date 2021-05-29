package broker.twotier.vo;
/*
 * 주식에 대한 정보를 저장하는 Record Class.
 */
public class StockRec {
	private String symbol;
	private float price;
	
	public StockRec(String symbol, float price) {
		super();
		this.symbol = symbol;
		this.price = price;
	}

	public StockRec() {
		symbol = "";
		price =  0.0F;		
	}

	public String getSymbol() {
		return symbol;
	}

	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "StockRec [symbol=" + symbol + ", price=" + price + "]";
	}	
}








