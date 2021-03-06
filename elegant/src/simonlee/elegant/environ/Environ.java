package simonlee.elegant.environ;


import simonlee.elegant.ELEGANT;
import simonlee.elegant.d3algo.AbstractD3Algo;
import simonlee.elegant.models.ApiContext;
import simonlee.elegant.utils.PubSub;
import soot.Scene;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.android.manifest.ProcessManifest;
import soot.jimple.infoflow.solver.cfg.IInfoflowCFG;
import soot.jimple.infoflow.solver.cfg.InfoflowCFG;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.util.*;

public class Environ implements PubSub.Handle {

    // Environment variable of ELEGANT

    // k neighbors, used in backward slicing
    public static final int ENV_K_NEIGHBORS = 10;
    // k-indirect-caller, used in call site computing
    // 0-indirect-caller is its direct caller
    public static final int ENV_K_INDIRECT_CALLER = 5;

    // Environments

    // elegant is the container that Env is in
    private ELEGANT elegant;

    // soot-analysed results
    private Set<ApiContext>  models;
    private SetupApplication app;
    private ProcessManifest  manifest;
    private AbstractD3Algo   d3Algo;
    private IInfoflowCFG     interproceduralCFG;

    public Environ(ELEGANT elegant) {
        this.elegant = elegant;

        // do some registration work, Env need to get arguments throwed by configurations
        this.elegant.watchOpts(this);
    }

    public SetupApplication getApp() {
        return app;
    }

    public ProcessManifest getManifest() {
        return manifest;
    }

    public AbstractD3Algo getD3Algo() {
        return d3Algo;
    }

    public Set<ApiContext> getModels() {
        return models;
    }

    public IInfoflowCFG getInterproceduralCFG() {
        return interproceduralCFG == null ? (interproceduralCFG = new InfoflowCFG()) : interproceduralCFG;
    }

    public CallGraph getCallGraph() {
        return Scene.v().getCallGraph();
    }

    public String getAppName() {
        return this.manifest.getApplicationName();
    }

    public String getAppPackage() {
        return this.manifest.getPackageName();
    }

    @Override
    public void handle(PubSub.Message message) {
        if (!(message instanceof OptParser.OptBundle)) {
            return;
        }

        OptParser.OptBundle bundle = (OptParser.OptBundle) message;
        String key = (String) bundle.getK();

        switch (key) {
            case OptParser.OPT_APK_PATH:
                this.app = (SetupApplication) bundle.getExtra(OptParser.OPT_BDL_APK_PATH_APP);
                this.manifest = (ProcessManifest) bundle.getExtra(OptParser.OPT_BDL_APK_PATH_MANIFEST);
                break;
            case OptParser.OPT_MODELS_PATH:
                this.models = (Set) bundle.getExtra(OptParser.OPT_BDL_MODELS_MODEL);
                break;
            case OptParser.OPT_PLATFORMS_PATH:
                break;
            case OptParser.OPT_D3_ALGO:
                this.d3Algo = (AbstractD3Algo) bundle.getExtra(OptParser.OPT_BDL_D3_ALGO_ALGO);
                break;
            default: /* we will pass unknown configs */
                break;
        }
    }
}
