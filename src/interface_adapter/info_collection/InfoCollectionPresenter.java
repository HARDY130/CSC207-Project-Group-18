//package interface_adapter.info_collection;
//
//public class InfoCollectionPresenter {
//
//        private InfoCollectionViewModel viewModel;
//        private InfoCollectionState state;
//
//        public InfoCollectionPresenter(InfoCollectionViewModel viewModel, InfoCollectionState state) {
//            this.viewModel = viewModel;
//            this.state = state;
//        }
//
//        public void updateViewModel() {
//            viewModel.setUserName(state.getUserName());
//            viewModel.setPassword(state.getPassword());
//        }
//
//        public void updateState() {
//            state.setUserName(viewModel.getUserName());
//            state.setPassword(viewModel.getPassword());
//        }
//
//        public InfoCollectionViewModel getViewModel() {
//            return viewModel;
//        }
//
//        public InfoCollectionState getState() {
//            return state;
//        }
//}
