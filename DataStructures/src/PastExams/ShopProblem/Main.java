public class Main {
    public static void main(String[] args) {

    }
}
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.OpenOption;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Main {
//    public static void main(String[] args) throws IOException {
//        List<Path> collect =
//                Files.walk(Paths.get("E:\\Repositories\\Data-Structures-Fund-Retake\\src\\test\\java\\core"))
//                        .skip(1)
//                        .collect(Collectors.toList());
//
//        int zeroTestsCounter = 1;
//        int contestTestsCounter = 1;
//
//        int totalContestTests = (int)collect.stream()
//                .filter(p -> !p.toString().contains("00"))
//                .count() + 1;
//
//        for (int i = 0; i < collect.size(); i++) {
//            Path path = collect.get(i);
//            if (path.toString().contains("00")) {
//                List<String> lines = Files.readAllLines(path);
//                lines.set(0, "import core.*;");
//                Files.writeString(Path.of("tests\\" + String.format("test.000.%03d.in.txt", zeroTestsCounter)),
//                        lines.stream().collect(Collectors.joining(System.lineSeparator()))
//                );
//                Files.writeString(Path.of("tests\\" + String.format("test.000.%03d.out.txt", zeroTestsCounter)),
//                        "Test Passed!"
//                );
//                zeroTestsCounter++;
//
//                lines.set(0, "import core.*;");
//                Files.writeString(Path.of("tests\\" + String.format("test.%03d.in.txt", totalContestTests)),
//                        lines.stream().collect(Collectors.joining(System.lineSeparator()))
//                );
//                Files.writeString(Path.of("tests\\" + String.format("test.%03d.out.txt", totalContestTests)),
//                        "Test Passed!"
//                );
//                totalContestTests++;
//
//            } else {
//                List<String> lines = Files.readAllLines(path);
//                lines.set(0, "import core.*;");
//                Files.writeString(Path.of("tests\\" + String.format("test.%03d.in.txt", contestTestsCounter)),
//                        lines.stream().collect(Collectors.joining(System.lineSeparator()))
//                );
//                Files.writeString(Path.of("tests\\" + String.format("test.%03d.out.txt", contestTestsCounter)),
//                        "Test Passed!"
//                );
//                contestTestsCounter++;
//            }
//        }
//    }
//}
