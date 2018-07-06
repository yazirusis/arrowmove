package game.value;

//終了ステータス
public class FinishStatus {
	public enum StatusCode {
		UNFINISH(-1),
		SUCCESS(0),
		ABNORMAL(1),;



		private int code;
		private StatusCode(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
	}
	private boolean isFinish;
	private StatusCode code;

	//デフォルトのステータスコードつかって生成
	public FinishStatus(boolean isFinish) {
		this.isFinish = isFinish;
		if (isFinish) {
			this.code = StatusCode.SUCCESS;
		}else {
			this.code = StatusCode.UNFINISH;
		}
	}
	public FinishStatus(boolean isFinish,StatusCode code) {
		this.isFinish = isFinish;
		this.code = code;
	}

	
	public boolean isFinish() {
		return isFinish;
	}


}
