package simonlee.elegant.finder;

import simonlee.elegant.models.ApiContext;
import simonlee.elegant.utils.PubSub;

public class Issue implements PubSub.Message {

    protected ApiContext model;

    public Issue(ApiContext model) {
        this.model = model;
    }

    public ApiContext getModel() {
        return model;
    }

    public void setModel(ApiContext model) {
        this.model = model;
    }
}
