package game.value

//終了ステータス
class FinishStatus {
    var isFinish: Boolean = false
        private set
    private var code: StatusCode? = null

    enum class StatusCode private constructor(val code: Int) {
        UNFINISH(-1),
        SUCCESS(0),
        ABNORMAL(1)
    }

    //デフォルトのステータスコードつかって生成
    constructor(isFinish: Boolean) {
        this.isFinish = isFinish
        if (isFinish) {
            this.code = StatusCode.SUCCESS
        } else {
            this.code = StatusCode.UNFINISH
        }
    }

    constructor(isFinish: Boolean, code: StatusCode) {
        this.isFinish = isFinish
        this.code = code
    }


}
