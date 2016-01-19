package com.xebia.reactnative;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class JavaModifier {
  public static final String TAB_LAYOUT_PACKAGE_CLASS = "com.xebia.reactnative.TabLayoutPackage";
  public static final String TAB_LAYOUT_PACKAGE_NAME = "TabLayoutPackage";
  public static final String GET_PACKAGES_METHOD = "getPackages";

  private String mainActivityPath;
  private CompilationUnit compilationUnit;

  public void updateMainActivity() throws Exception {
    findMainActivityPath();
    if (mainActivityPath == null) {
      throw new FileNotFoundException("MainActivity.java not found");
    }

    FileInputStream in = new FileInputStream(mainActivityPath);
    try {
      compilationUnit = JavaParser.parse(in);
    } finally {
      in.close();
    }

    boolean importsChanged = updateImports();
    boolean modulesChanged = updateModules();

    if (importsChanged && modulesChanged) {
      InstallerUtil.writeToDisk(mainActivityPath, compilationUnit.toString());
      System.out.println("Updated import and module statements in " + mainActivityPath);
    } else if (!importsChanged && !modulesChanged) {
      System.out.println("Import and module statements were not updated, is the class " + mainActivityPath + " updated already?");
    } else {
      throw new Exception("Could not update import or module, update your code manually");
    }
  }

  private boolean updateImports() {
    List<ImportDeclaration> imports = compilationUnit.getImports();
    if (!hasImport(imports, TAB_LAYOUT_PACKAGE_CLASS)) {
      imports.add(new ImportDeclaration(new NameExpr(TAB_LAYOUT_PACKAGE_CLASS), false, false));
      return true;
    }
    return false;
  }

  private boolean updateModules() {
    List<TypeDeclaration> types = compilationUnit.getTypes();
    for (TypeDeclaration type : types) {
      List<BodyDeclaration> members = type.getMembers();
      for (BodyDeclaration member : members) {
        if (!(member instanceof MethodDeclaration)) {
          continue;
        }
        MethodDeclaration method = (MethodDeclaration) member;
        if (!GET_PACKAGES_METHOD.equals(method.getName())) {
          continue;
        }
        for (Statement stmt : method.getBody().getStmts()) {
          if (!(stmt instanceof ReturnStmt)) {
            continue;
          }
          Expression returnStmtExpr = ((ReturnStmt) stmt).getExpr();
          if (!(returnStmtExpr instanceof MethodCallExpr)) {
            continue;
          }
          MethodCallExpr callExpr = (MethodCallExpr) returnStmtExpr;
          if (callExpr.getArgs() == null) {
            continue;
          }
          List<Expression> moduleList = callExpr.getArgs();
          if (hasModule(moduleList, TAB_LAYOUT_PACKAGE_NAME)) {
            continue;
          }
          moduleList.add(new ObjectCreationExpr(null, new ClassOrInterfaceType(TAB_LAYOUT_PACKAGE_NAME), null));
          return true;
        }
      }
    }
    return false;
  }

  private void findMainActivityPath() throws IOException {
    Files.walkFileTree(Paths.get("android/app/src/main/java"), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path name = file.getFileName();
        if (name != null && name.toString().equals("MainActivity.java")) {
          mainActivityPath = file.toAbsolutePath().toString();
          return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
      }
    });
  }

  private boolean hasModule(List<Expression> moduleList, String packageName) {
    for (Expression moduleExpr : moduleList) {
      if (moduleExpr instanceof ObjectCreationExpr) {
        ObjectCreationExpr objectCreationExpr = (ObjectCreationExpr) moduleExpr;
        if (packageName.equals(objectCreationExpr.getType().toString())) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean hasImport(List<ImportDeclaration> imports, String importName) {
    for (ImportDeclaration i : imports) {
      if (importName.equals(i.getName().toString())) {
        return true;
      }
    }
    return false;
  }
}
