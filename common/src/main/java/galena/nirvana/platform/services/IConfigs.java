package galena.nirvana.platform.services;

import galena.nirvana.config.NirvanaClientConfig;
import galena.nirvana.config.NirvanaCommonConfig;

public interface IConfigs {

    NirvanaCommonConfig common();

    NirvanaClientConfig client();

}
