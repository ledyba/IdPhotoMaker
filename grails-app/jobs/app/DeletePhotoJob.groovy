package app


class DeletePhotoJob {
    static triggers = {
        simple name: 'PhotoDeleteTrigger', startDelay: 60*10*1000, repeatInterval: 60*10*1000
    }

    def group = "PhotoDelete"

    def execute() {
        Photo.executeUpdate("delete Photo p where p.lastUpdated < ?",[(new Date((new Date()).time - 60*10*1000))]);
    }
}
