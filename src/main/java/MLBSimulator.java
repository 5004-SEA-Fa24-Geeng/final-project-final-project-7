import controller.MLBSimulatorController;

class MLBSimulator {

  MLBSimulatorController controller;

  public static void main(String[] args) {
    MLBSimulatorController controller = new MLBSimulatorController();
    controller.start();
  }
}
